package com.befoodly.be.repository;

import com.befoodly.be.entity.AuthLog;
import com.befoodly.be.model.enums.AppPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthLogRepository extends JpaRepository<AuthLog, Long> {
//    void deleteByReferenceId(String referenceId);
    Optional<AuthLog> findByReferenceId(String referenceId);
//    Optional<AuthLog> findByPhoneNumberAndAppPlatform(String phoneNumber, AppPlatform appPlatform);
//    Optional<AuthLog> findByPhoneNumberAndAppPlatformAndIsExpired(String phoneNumber, AppPlatform appPlatform, Boolean isExpired);
//    Optional<AuthLog> findByAndAppPlatformAndIsExpired(String sessionToken, AppPlatform appPlatform, Boolean isExpired);
//    Optional<AuthLog> findBySessionTokenAndAppPlatform(String sessionToken, AppPlatform appPlatform);
//    Optional<AuthLog> findByPhoneNumber(String phoneNumber);
}
