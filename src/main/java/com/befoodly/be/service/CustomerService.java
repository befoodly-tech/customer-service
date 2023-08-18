package com.befoodly.be.service;

import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.model.request.CustomerCreateRequest;
import com.befoodly.be.model.request.CustomerEditRequest;

import java.util.Optional;

public interface CustomerService {
    CustomerEntity createCustomer(CustomerCreateRequest request);
    CustomerEntity editCustomerDetails(String customerReferenceId, CustomerEditRequest request);
    CustomerEntity fetchCustomerDetails(String customerReferenceId);
}
