package com.befoodly.be.dao;

import com.befoodly.be.entity.Customer;

import java.util.Optional;

public interface CustomerDataDao {
    void save(Customer customer);
    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);
    Optional<Customer> findCustomerByEmail(String email);
    Customer findCustomerByReferenceId(String customerReferenceId);
}
