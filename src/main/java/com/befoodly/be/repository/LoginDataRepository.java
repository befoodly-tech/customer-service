package com.befoodly.be.repository;

import com.befoodly.be.entity.LoginDataEntity;
import com.befoodly.be.model.enums.AppPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginDataRepository extends JpaRepository<LoginDataEntity, Long> {
    void deleteByReferenceId(String referenceId);

    Optional<LoginDataEntity> findByReferenceId(String referenceId);

    Optional<LoginDataEntity> findByPhoneNumberAndAppPlatform(String phoneNumber, AppPlatform appPlatform);

    Optional<LoginDataEntity> findByPhoneNumberAndAppPlatformAndIsExpired(String phoneNumber, AppPlatform appPlatform, Boolean isExpired);
}
