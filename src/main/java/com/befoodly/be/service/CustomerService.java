package com.befoodly.be.service;

import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.model.request.CustomerCreateRequest;
import com.befoodly.be.model.request.CustomerEditRequest;

public interface CustomerService {
    String createCustomer(CustomerCreateRequest request);
    CustomerEntity editCustomerDetails(String phoneNumber, CustomerEditRequest request);
}
