package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.LoginDataDao;
import com.befoodly.be.entity.LoginDataEntity;
import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.repository.LoginDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LoginDataDaoImpl implements LoginDataDao {
    private final LoginDataRepository loginDataRepository;

    @Override
    public void save(@NonNull LoginDataEntity loginDataEntity) {
        loginDataRepository.saveAndFlush(loginDataEntity);
    }

    @Override
    public Optional<LoginDataEntity> findUserByReferenceId (String referenceId) {
        return loginDataRepository.findByReferenceId(referenceId);
    }

    @Override
    public void deleteUserByReferenceId(String referenceId) {
        loginDataRepository.deleteByReferenceId(referenceId);
    }

    @Override
    public Optional<LoginDataEntity> findLastLoggedInUser(String phoneNumber, AppPlatform appPlatform) {
        return loginDataRepository.findByPhoneNumberAndAppPlatform(phoneNumber, appPlatform);
    }

    @Override
    public Optional<LoginDataEntity> findActiveUserByPhoneNumber(String phoneNumber, AppPlatform appPlatform) {
        return loginDataRepository.findByPhoneNumberAndAppPlatformAndIsExpired(phoneNumber, appPlatform, false);
    }
}
