package com.befoodly.be.dao;

import com.befoodly.be.entity.AuthLog;
import com.befoodly.be.model.enums.AppPlatform;

import java.util.Optional;

public interface LoginDataDao {
    void save(AuthLog authLog);
//    Optional<AuthLog> findUserByReferenceId(String referenceId);
//    void deleteUserByReferenceId(String referenceId);
//    Optional<AuthLog> findLastLoggedInUser(String phoneNumber, AppPlatform appPlatform);
//    Optional<AuthLog> findActiveUserByPhoneNumber(String phoneNumber, AppPlatform appPlatform);
//    Optional<AuthLog> findActiveUserBySessionToken(String sessionToken, AppPlatform appPlatform);
//    Optional<AuthLog> findLoggedInDetailBySession(String sessionToken, AppPlatform appPlatform);
}
