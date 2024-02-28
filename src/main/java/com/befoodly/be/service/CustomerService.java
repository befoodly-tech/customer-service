package com.befoodly.be.service;

import com.befoodly.be.entity.Customer;
import com.befoodly.be.model.request.CustomerCreateRequest;
import com.befoodly.be.model.request.CustomerEditRequest;

public interface CustomerService {
    Customer createCustomer(CustomerCreateRequest request);
    Customer editCustomerDetails(String customerReferenceId, CustomerEditRequest request);
    Customer fetchCustomerDetails(String customerReferenceId);
}
