package com.befoodly.be.repository;

import com.befoodly.be.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByReferenceId(String customerReferenceId);

    Optional<Customer> findByEmail(String email);
}
