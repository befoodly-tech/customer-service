package com.befoodly.be.service.impl;

import com.befoodly.be.clients.AwsSnsClient;
import com.befoodly.be.clients.Msg91EmailClient;
import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.dao.LoginDataDao;
import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.entity.LoginDataEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.enums.ExpiryReason;
import com.befoodly.be.model.request.CustomerCreateRequest;
import com.befoodly.be.model.response.EmailLoginResponse;
import com.befoodly.be.model.response.LoginResponse;
import com.befoodly.be.service.CustomerService;
import com.befoodly.be.service.LoginService;
import com.befoodly.be.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

import static com.befoodly.be.model.constant.CommonConstants.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginDataDao loginDataDao;

    private final CustomerService customerService;

    private final CustomerDataDao customerDataDao;

    private final AwsSnsClient awsSnsClient;

    private final Msg91EmailClient msg91EmailClient;

    @Override
    @Transactional
    public EmailLoginResponse signUpUser(String phoneNumber, AppPlatform appPlatform) {

        try {
            if (StringUtils.isEmpty(phoneNumber)) {
                return null;
            }

            Optional<CustomerEntity> customerEntity = customerDataDao.findCustomerByPhoneNumber(phoneNumber);

            if (customerEntity.isPresent()) {
                log.info("Customer with phone number: {} already exist!", phoneNumber);
                throw new InvalidException("This phone number is already registered!");
            }

            LoginDataEntity newLoginData = loginNewEntryInTable(phoneNumber, appPlatform);

            String otpMessage = getOtpMessage(newLoginData.getOtp());
            awsSnsClient.sendOTPMessage(otpMessage, phoneNumberWithCode(phoneNumber));

            loginDataDao.save(newLoginData);
            log.info("Added the sign up details of user with number: {}", phoneNumber);

            return EmailLoginResponse.builder()
                    .sessionToken(newLoginData.getSessionToken())
                    .phoneNumber(phoneNumber)
                    .build();

        } catch (Exception e) {
            log.error("Received error while sign in: {} with number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    @Override
    public EmailLoginResponse loginUser(String email, AppPlatform appPlatform) {

        try {
            Optional<CustomerEntity> customerEntity = customerDataDao.findCustomerByEmail(email);

            if (customerEntity.isEmpty()) {
                log.info("Customer with email: {} not found!", email);
                throw new InvalidException("No Customer registered with email!");
            }

            CustomerEntity currentCustomer = customerEntity.get();
            LoginDataEntity newLoginData = loginNewEntryInTable(currentCustomer.getPhoneNumber(), appPlatform);

            msg91EmailClient.sendOTPMail(newLoginData.getOtp(), email);

            loginDataDao.save(newLoginData);
            log.info("Added the new login details of user with email: {}", email);

            return EmailLoginResponse.builder()
                    .phoneNumber(currentCustomer.getPhoneNumber())
                    .sessionToken(newLoginData.getSessionToken())
                    .build();
        } catch (Exception e) {
            log.error("Received following error: {}, while logging in with email: {}", e.getMessage(), email);
            throw e;
        }
    }

    private String getOtpMessage(String otp) {
        return "Hello! from BeFoodly, kindly verify your phone number with one-time password(OTP): " + otp;
    }

    private LoginDataEntity loginNewEntryInTable(String phoneNumber, AppPlatform appPlatform) {
        try {
            Optional<LoginDataEntity> loginDataEntity = loginDataDao.findActiveUserByPhoneNumber(phoneNumber, appPlatform);

            if (loginDataEntity.isPresent()) {
                LoginDataEntity currentLoginData = loginDataEntity.get();
                currentLoginData.setIsExpired(true);
                currentLoginData.setExpiryReason(ExpiryReason.SESSION_EXPIRE);
                loginDataDao.save(currentLoginData);
            }

            String referenceId = UUID.randomUUID().toString();
            String sessionToken = UUID.randomUUID().toString();
            String otp = CommonUtils.GenerateOtp();

            LoginDataEntity addUserData = LoginDataEntity.builder()
                    .referenceId(referenceId)
                    .appPlatform(appPlatform)
                    .sessionToken(sessionToken)
                    .phoneNumber(phoneNumber)
                    .isExpired(false)
                    .otp(otp)
                    .otpVerified(false)
                    .build();

            log.info("New entry created with reference id: {}", referenceId);

            return addUserData;
        } catch (Exception e) {
            log.error("Received error: {} while adding new login entry for number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    @Override
    @Transactional
    public String logoutUser(String sessionToken, AppPlatform appPlatform) {

        try {
            if (StringUtils.isEmpty(sessionToken)) {
                return null;
            }

            Optional<LoginDataEntity> currentData = loginDataDao.findActiveUserBySessionToken(sessionToken, appPlatform);

            if (currentData.isEmpty()) {
                log.info("No active logged in data found for the session: {}", sessionToken);
                throw new InvalidException("No active logged in user with this data!");
            }

            LoginDataEntity loginData = currentData.get();

            loginData.setIsExpired(true);
            loginData.setExpiryReason(ExpiryReason.LOGOUT);

            loginDataDao.save(loginData);
            log.info("Successfully! expired the session token for previous logged in user: {}", loginData.getPhoneNumber());

            return SUCCESS_LOGOUT_MESSAGE;

        } catch (Exception e) {
            log.error("user logout for session: {} failed because of error: {}", sessionToken, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public LoginResponse otpVerification(String otp, String phoneNumber, AppPlatform appPlatform) {

        try {
            Optional<LoginDataEntity> currentData = loginDataDao.findActiveUserByPhoneNumber(phoneNumber, appPlatform);

            if (currentData.isEmpty()) {
                log.info("No active user found for number: {}", phoneNumber);
                throw new InvalidException("No active data found with phone number!");
            }

            LoginDataEntity loginData = currentData.get();
            String currentOtp = loginData.getOtp();

            if (currentOtp.equals(otp) && !loginData.getOtpVerified()) {
                loginData.setOtpVerified(true);
                loginData.setOtp(null);

                loginDataDao.save(loginData);
                log.info("otp verification done for phoneNumber: {}", phoneNumber);

                Optional<CustomerEntity> customerEntity = customerDataDao.findCustomerByPhoneNumber(phoneNumber);
                LoginResponse loginResponse = LoginResponse.builder()
                        .id(loginData.getId())
                        .referenceId(loginData.getReferenceId())
                        .build();

                if (customerEntity.isEmpty()) {
                    CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                            .phoneNumber(phoneNumber)
                            .sessionToken(loginData.getSessionToken())
                            .build();

                    CustomerEntity customer = customerService.createCustomer(customerCreateRequest);
                    loginResponse.setCustomerData(customer);
                    loginResponse.setIsCustomerDataExist(false);
                } else {
                    CustomerEntity customer = customerEntity.get();
                    customer.setSessionToken(loginData.getSessionToken());
                    customer.setIsActive(true);

                    loginResponse.setCustomerData(customer);
                    if (StringUtils.isEmpty(customer.getAddress())) {
                        loginResponse.setIsCustomerDataExist(false);
                    } else {
                        loginResponse.setIsCustomerDataExist(true);
                    }

                    customerDataDao.save(customer);
                }

                log.info("Successfully, created the customer data for phoneNumber: {}", phoneNumber);

                return loginResponse;
            } else {
                log.info("Failed to verify the user number: {}", phoneNumber);
                throw new InvalidException("Invalid OTP Entered!");
            }

        } catch (Exception e) {
            log.error("Received error while otp verification: {}, for phone number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    @Override
    @Transactional
    public String resendOtp(String phoneNumber, AppPlatform appPlatform) {

        try {
            Optional<LoginDataEntity> currentData = loginDataDao.findActiveUserByPhoneNumber(phoneNumber, appPlatform);

            if (currentData.isEmpty()) {
                log.info("No login data found for the phone number: {}", phoneNumber);
                throw new InvalidException("Invalid phone number!");
            }

            LoginDataEntity loginData = currentData.get();
            loginData.setOtp(CommonUtils.GenerateOtp());
            loginData.setOtpVerified(false);

            loginDataDao.save(loginData);
            log.info("resent otp for phone number: {}", phoneNumber);

            return SUCCESS_MESSAGE;

        } catch (Exception e) {
            log.error("Received error while resend otp: {}, for phone number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    @Override
    @Transactional
    public void editLoginNumber(String phoneNumber, AppPlatform appPlatform) {

        try {
            Optional<LoginDataEntity> entity = loginDataDao.findActiveUserByPhoneNumber(phoneNumber, appPlatform);

            if (entity.isEmpty()) {
                log.info("No active user found with phone number: {}", phoneNumber);
                throw new InvalidException("Invalid Phone Number Entered!");
            }

            log.info("deletes user to edit the login number");
            loginDataDao.deleteUserByReferenceId(entity.get().getReferenceId());
        } catch (Exception e) {
            log.error("Received error while resend otp: {}, for phone number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    @Override
    public Boolean checkExpiryOfSessionToken(String sessionToken, AppPlatform appPlatform) {
        try {
            Optional<LoginDataEntity> entity = loginDataDao.findLoggedInDetailBySession(sessionToken, appPlatform);

            if (entity.isEmpty()) {
                log.info("No data with this session token: {} found", sessionToken);
                throw new InvalidException("Invalid session token!");
            }

            return entity.get().getIsExpired();
         } catch (Exception e) {
            log.error("Received error: {} while checking status for session token: {}", e.getMessage(), sessionToken);
            throw e;
        }
    }

    private String phoneNumberWithCode (String phoneNumber) {
        return "+91" + phoneNumber;
    }
}
