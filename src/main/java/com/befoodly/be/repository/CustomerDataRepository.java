package com.befoodly.be.repository;

import com.befoodly.be.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);

    Optional<CustomerEntity> findByReferenceId(String customerReferenceId);
}
