package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.LoginDataDao;
import com.befoodly.be.entity.AuthLog;
import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.repository.AuthLogRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LoginDataDaoImpl implements LoginDataDao {
    private final AuthLogRepository authLogRepository;

    @Override
    public void save(@NonNull AuthLog authLog) {
        authLogRepository.saveAndFlush(authLog);
    }

//    @Override
//    public Optional<AuthLog> findUserByReferenceId (String referenceId) {
//        return authLogRepository.findByReferenceId(referenceId);
//    }

//    @Override
//    public void deleteUserByReferenceId(String referenceId) {
//        authLogRepository.deleteByReferenceId(referenceId);
//    }

//    @Override
//    public Optional<AuthLog> findLastLoggedInUser(String phoneNumber, AppPlatform appPlatform) {
//        return authLogRepository.findByPhoneNumberAndAppPlatform(phoneNumber, appPlatform);
//    }

//    @Override
//    public Optional<AuthLog> findActiveUserByPhoneNumber(String phoneNumber, AppPlatform appPlatform) {
//        return authLogRepository.findByPhoneNumberAndAppPlatformAndIsExpired(phoneNumber, appPlatform, false);
//    }

//    @Override
//    public Optional<AuthLog> findActiveUserBySessionToken(String sessionToken, AppPlatform appPlatform) {
//        return authLogRepository.findBySessionTokenAndAppPlatformAndIsExpired(sessionToken, appPlatform, false);
//    }

//    @Override
//    public Optional<AuthLog> findLoggedInDetailBySession(String sessionToken, AppPlatform appPlatform) {
//        return authLogRepository.findBySessionTokenAndAppPlatform(sessionToken, appPlatform);
//    }
}
