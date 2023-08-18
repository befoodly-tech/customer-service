package com.befoodly.be.dao;

import com.befoodly.be.entity.CustomerEntity;

import java.util.Optional;

public interface CustomerDataDao {
    void save(CustomerEntity customerEntity);
    Optional<CustomerEntity> findCustomerByPhoneNumber(String phoneNumber);
    CustomerEntity findCustomerByReferenceId(String customerReferenceId);
}
