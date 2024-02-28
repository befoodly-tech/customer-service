package com.befoodly.be.service.impl;

import com.befoodly.be.clients.AwsSnsClient;
import com.befoodly.be.entity.AuthLog;
import com.befoodly.be.entity.Customer;
import com.befoodly.be.entity.RefreshToken;
import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.request.GenerateOtpRequest;
import com.befoodly.be.model.request.RefreshTokenRequest;
import com.befoodly.be.model.request.VerifyOtpRequest;
import com.befoodly.be.model.response.GenerateOtpResponse;
import com.befoodly.be.model.response.RefreshTokenResponse;
import com.befoodly.be.model.response.VerifyOtpResponse;
import com.befoodly.be.repository.AuthLogRepository;
import com.befoodly.be.repository.CustomerRepository;
import com.befoodly.be.repository.RefreshTokenRepository;
import com.befoodly.be.service.LoginService;
import com.befoodly.be.utils.CommonUtils;
import com.befoodly.be.utils.JwtUtil;
import com.befoodly.be.utils.PhoneNumberValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthLogRepository authLogRepository;
    private final CustomerRepository customerRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    private final AwsSnsClient awsSnsClient;

    private final static String COUNTRY_CODE = "91";

    @Override
    @Transactional
    public GenerateOtpResponse generateOtp(GenerateOtpRequest request, AppPlatform appPlatform) {
        try {
            if (!PhoneNumberValidatorUtil.isValidPhoneNumber(request.getPhoneNumber())) {
                //TODO : Throw custom exception
                throw new RuntimeException(" ");
            }

            String otp = CommonUtils.GenerateOtp();

            AuthLog authLog = AuthLog.builder().referenceId(UUID.randomUUID().toString()).otp(otp).phoneNumber(request.getPhoneNumber()).isValidated(false).validationAttempts(0).expirationTime(LocalDateTime.now().plusMinutes(15)).platform(appPlatform).build();
            authLogRepository.saveAndFlush(authLog);

            String otpMessage = getOtpMessage(otp);
            //awsSnsClient.sendOTPMessage(otpMessage, request.getPhoneNumber());
            //TODO: Save to communication history

            log.info("Added the sign up details of user with number: {}", request.getPhoneNumber());
            return GenerateOtpResponse.builder().otpReferenceId(authLog.getReferenceId()).phoneNumber(request.getPhoneNumber()).build();

        } catch (Exception e) {
            log.error("Received error while generating otp for phone number: {}", request.getPhoneNumber(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request, AppPlatform appPlatform) {
        try {
            AuthLog authLog = authLogRepository.findByReferenceId(request.getOtpReferenceId()).orElseThrow(() -> new RuntimeException(" ")); //TODO : Custom Exception

            //TODO: Throw custom exceptions for each otp verification failing step
            if (authLog.getOtp().equals(request.getOtp()) && (authLog.getExpirationTime().isAfter(LocalDateTime.now())) && !authLog.getIsValidated() && authLog.getValidationAttempts() < 3) {

                log.info("OTP verified for phone number: {}", authLog.getPhoneNumber());

                Customer customer = customerRepository.findByPhoneNumber(authLog.getPhoneNumber())
                        .orElse(Customer.builder()
                                .referenceId(UUID.randomUUID().toString())
                                .externalReferenceId(UUID.randomUUID().toString())
                                .phoneNumber(authLog.getPhoneNumber())
                                .build());

                RefreshToken refreshToken = RefreshToken.builder().isExpired(false).referenceId(UUID.randomUUID().toString()).referenceId(UUID.randomUUID().toString()).expirationTime(LocalDateTime.now().plusMinutes(259200)) //TODO: 6 months expiry, move to config
                        .platform(appPlatform).build();
                refreshTokenRepository.save(refreshToken);

                return VerifyOtpResponse.builder()
                        .customerReferenceId(customer.getReferenceId())
                        .refreshToken(refreshToken.getReferenceId())
                        .authToken(createAuthenticationToken(customer.getReferenceId()))
                        .authType("Bearer ")
                        .build();
            } else {
                throw new RuntimeException(" "); //TODO: Throw custom exceptions for each failing usecase
            }
        } catch (Exception e) {
            log.error("Received error while verifying otp for phone number: {}", request.getPhoneNumber(), e);
            throw new RuntimeException(" ");
        }
    }

    public String createAuthenticationToken(String customerReferenceId) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerReferenceId, ""));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(customerReferenceId);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest, AppPlatform appPlatform) {
        return refreshTokenRepository.findByReferenceId(refreshTokenRequest.getRefreshToken()).map(refreshToken -> {
            if (refreshToken.isExpired()) {
                throw new RuntimeException(" "); //TODO: Custom Exception
            }
            if (LocalDateTime.now().isAfter(refreshToken.getExpirationTime())) {
                refreshToken.setExpired(true);
                refreshTokenRepository.save(refreshToken);
                throw new RuntimeException(" "); //TODO: Custom Exception
            } else {
                try {
                    String authToken = createAuthenticationToken(refreshToken.getCustomerReferenceId());
                    return RefreshTokenResponse.builder()
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .authToken(authToken)
                            .authType("Bearer ").build();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).orElseThrow(() -> new RuntimeException(" "));
    }

    //Lets allow user to login into multiple devices
    @Override
    @Transactional
    public String logoutUser(String sessionToken, AppPlatform appPlatform) {
        return "Logout successful!!";
    }

    private String getOtpMessage(String otp) {
        return "Hello! from BeFoodly, kindly verify your phone number with one-time password(OTP): " + otp;
    }

    private String phoneNumberWithCode(String phoneNumber) {
        return String.format("%s%s", COUNTRY_CODE, phoneNumber);
    }
}
