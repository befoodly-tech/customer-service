package com.befoodly.be.dao;

import com.befoodly.be.entity.LoginDataEntity;
import com.befoodly.be.model.enums.AppPlatform;

import java.util.Optional;

public interface LoginDataDao {
    void save(LoginDataEntity loginDataEntity);

    Optional<LoginDataEntity> findUserByReferenceId(String referenceId);

    void deleteUserByReferenceId(String referenceId);

    Optional<LoginDataEntity> findLastLoggedInUser(String phoneNumber, AppPlatform appPlatform);

    Optional<LoginDataEntity> findActiveUserByPhoneNumber(String phoneNumber, AppPlatform appPlatform);
}
