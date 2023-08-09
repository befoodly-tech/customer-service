package com.befoodly.be.service.impl;

import com.befoodly.be.clients.AwsSnsClient;
import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.dao.LoginDataDao;
import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.entity.LoginDataEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.enums.ExpiryReason;
import com.befoodly.be.model.request.CustomerCreateRequest;
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

    @Override
    public String loginUser(String phoneNumber, AppPlatform appPlatform) {

        try {
            if (StringUtils.isEmpty(phoneNumber)) {
                return null;
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

            String otpMessage = getOtpMessage(otp);
            awsSnsClient.sendOTPMessage(otpMessage, phoneNumber);

            loginDataDao.save(addUserData);
            log.info("Added the login details of user with number: {}", phoneNumber);

            return sessionToken;

        } catch (Exception e) {
            log.error("Received error while logging in: {} with number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    private String getOtpMessage(String otp) {
        return "Hello! from BeFoodly, kindly verify your phone number with one-time password(OTP): " + otp;
    }

    @Override
    @Transactional
    public String logoutUser(String phoneNumber, AppPlatform appPlatform) {

        try {
            if (StringUtils.isEmpty(phoneNumber)) {
                return null;
            }

            Optional<LoginDataEntity> currentData = loginDataDao.findActiveUserByPhoneNumber(phoneNumber, appPlatform);

            if (currentData.isEmpty()) {
                log.info("No active logged in data found for the number: {}", phoneNumber);
                throw new InvalidException("No active logged in user with phone number!");
            }

            Optional<CustomerEntity> customerEntity = customerDataDao.findCustomerByPhoneNumber(phoneNumber);

            if (customerEntity.isEmpty()) {
                log.info("No active customer details found with number: {}", phoneNumber);
                throw new InvalidException("Invalid phone number!");
            }

            LoginDataEntity loginData = currentData.get();
            CustomerEntity customerdata = customerEntity.get();

            loginData.setIsExpired(true);
            loginData.setExpiryReason(ExpiryReason.LOGOUT);

            customerdata.setIsActive(false);

            loginDataDao.save(loginData);
            log.info("Successfully! expired the session token for previous logged in user: {}", phoneNumber);

            return SUCCESS_LOGOUT_MESSAGE;

        } catch (Exception e) {
            log.error("user logout for phone number: {} failed because of error: {}", phoneNumber, e.getMessage());
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

                    loginResponse.setCustomerData(null);
                    loginResponse.setIsCustomerExist(false);

                    customerService.createCustomer(customerCreateRequest);
                } else {
                    CustomerEntity customer = customerEntity.get();
                    customer.setSessionToken(loginData.getSessionToken());
                    customer.setIsActive(true);

                    loginResponse.setCustomerData(customer);
                    loginResponse.setIsCustomerExist(true);

                    customerDataDao.save(customer);
                }

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
}
