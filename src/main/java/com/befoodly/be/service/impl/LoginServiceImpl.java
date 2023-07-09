package com.befoodly.be.service.impl;

import com.befoodly.be.dao.LoginDataDao;
import com.befoodly.be.entity.LoginDataEntity;
import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.enums.ExpiryReason;
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

    @Override
    public String loginUser(String phoneNumber, AppPlatform appPlatform) {

        try {
            if (StringUtils.isEmpty(phoneNumber)) {
                return null;
            }

            String referenceId = UUID.randomUUID().toString();

            LoginDataEntity addUserData = LoginDataEntity.builder()
                    .referenceId(referenceId)
                    .appPlatform(appPlatform)
                    .sessionToken(UUID.randomUUID().toString())
                    .phoneNumber(phoneNumber)
                    .isExpired(false)
                    .otp(CommonUtils.GenerateOtp())
                    .otpVerified(false)
                    .build();

            loginDataDao.save(addUserData);
            log.info("added the login details of new user!");

            return referenceId;

        } catch (Exception e) {
            log.error("Received error while logging in: {} with number: {}", e.getMessage(), phoneNumber);
            throw e;
        }
    }

    @Override
    @Transactional
    public String logoutUser(String phoneNumber, AppPlatform appPlatform) {

        try {
            if (StringUtils.isEmpty(phoneNumber)) {
                return null;
            }

            Optional<LoginDataEntity> currentData = loginDataDao.findLastLoggedInUser(phoneNumber, appPlatform);

            if (currentData.isPresent()) {
                LoginDataEntity loginData = currentData.get();
                loginData.setIsExpired(true);
                loginData.setExpiryReason(ExpiryReason.LOGOUT);

                loginDataDao.save(loginData);
                log.info("Successfully! expired the session token for previous logged in user: {}", phoneNumber);

                return SUCCESS_LOGOUT_MESSAGE;
            }

            return FAILURE_LOGOUT_MESSAGE;
        } catch (Exception e) {
            log.error("user logout for phone number: {} failed because of error: {}", phoneNumber, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public String otpVerification(String otp, String referenceId) {

        try {
            Optional<LoginDataEntity> currentData = loginDataDao.findUserByReferenceId(referenceId);

            if (currentData.isPresent()) {
                LoginDataEntity loginData = currentData.get();
                String currentOtp = loginData.getOtp();

                if (currentOtp.equals(otp) && !loginData.getOtpVerified()) {
                    loginData.setOtpVerified(true);
                    loginDataDao.save(loginData);
                    log.info("otp verification done for reference id: {}", referenceId);

                    return SUCCESS_OTP_MESSAGE;
                }
            }

            return FAILURE_OTP_MESSAGE;

        } catch (Exception e) {
            log.error("Received error while otp verification: {}, for reference Id: {}", e.getMessage(), referenceId);
            throw e;
        }
    }

    @Override
    @Transactional
    public String resendOtp(String referenceId) {

        try {
            Optional<LoginDataEntity> currentData = loginDataDao.findUserByReferenceId(referenceId);

            if (currentData.isPresent()) {
                LoginDataEntity loginData = currentData.get();
                loginData.setOtp(CommonUtils.GenerateOtp());
                loginData.setOtpVerified(false);

                loginDataDao.save(loginData);
                log.info("resent otp for reference id: {}", referenceId);

                return SUCCESS_MESSAGE;
            }

            return FAILURE_MESSAGE;

        } catch (Exception e) {
            log.error("Received error while resend otp: {}, for reference Id: {}", e.getMessage(), referenceId);
            throw e;
        }
    }

    @Override
    @Transactional
    public void editLoginNumber(String referenceId) {

        try {
            log.info("deletes user to edit the login number");
            loginDataDao.deleteUserByReferenceId(referenceId);
        } catch (Exception e) {
            log.error("Received error while resend otp: {}, for reference Id: {}", e.getMessage(), referenceId);
            throw e;
        }

    }
}
