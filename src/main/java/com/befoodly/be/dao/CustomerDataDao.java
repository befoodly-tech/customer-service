package com.befoodly.be.dao;

import com.befoodly.be.entity.CustomerEntity;

public interface CustomerDataDao {
    void save(CustomerEntity customerEntity);
    CustomerEntity findCustomerByPhoneNumber(String phoneNumber);
}
