package com.befoodly.be.repository;

import com.befoodly.be.entity.LoginDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginDataRepository extends JpaRepository<LoginDataEntity, Long> {
    void deleteByReferenceId(String referenceId);

    Optional<LoginDataEntity> findByReferenceId(String referenceId);

    @Query(nativeQuery = true, value = "select * from login_data where phone_number =:phoneNumber and app_platform =:appPlatform order by updated_at DESC limit 1")
    Optional<LoginDataEntity> findByPhoneNumberAndAppPlatform(String phoneNumber, String appPlatform);
}
