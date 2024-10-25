package com.befoodly.be.service.impl;

import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.request.AddressCreateRequest;
import com.befoodly.be.model.request.CustomerCreateRequest;
import com.befoodly.be.model.request.CustomerEditRequest;
import com.befoodly.be.service.AddressService;
import com.befoodly.be.service.CustomerService;
import com.befoodly.be.utils.CommonUtils;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDataDao customerDataDao;

    private final AddressService addressService;

    @Override
    public CustomerEntity createCustomer(CustomerCreateRequest request) {

        try {
            String referenceId = UUID.randomUUID().toString();

            CustomerEntity customer = CustomerEntity.builder()
                    .name("Foodie")
                    .referenceId(referenceId)
                    .phoneNumber(request.getPhoneNumber())
                    .sessionToken(request.getSessionToken())
                    .isActive(true)
                    .build();

            customerDataDao.save(customer);
            log.info("successfully saved the data for customer id: {}", referenceId);

            return customer;

        } catch(Exception e) {
            log.error("received error message while saving the customer data: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public CustomerEntity editCustomerDetails(String customerReferenceId, CustomerEditRequest request) {

        try {
            CustomerEntity customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

            if (ObjectUtils.isEmpty(customer)) {
                log.info("no customer found with customer id:{}", customerReferenceId);

                throw new InvalidException("invalid customer reference id!");
            }

            if (StringUtils.isNotEmpty(request.getName())) {
                customer.setName(request.getName());
            }

            if (StringUtils.isNotEmpty(request.getEmail())) {
                Optional<CustomerEntity> customer1 = customerDataDao.findCustomerByEmail(request.getEmail());

                if (customer1.isPresent()) {
                    log.info("This email id is already registered!");
                    throw new InvalidException("This Email is already registered!");
                }

                customer.setEmail(request.getEmail());
            }

            if (ObjectUtils.isNotEmpty(request.getAddress())) {
                AddressCreateRequest addressCreateRequest = request.getAddress();

                if (StringUtils.isEmpty(addressCreateRequest.getPhoneNumber())) {
                    addressCreateRequest.setPhoneNumber(customer.getPhoneNumber());
                }

                customer.setAddress(CommonUtils.convertToOneLineAddress(request.getAddress()));
                addressService.addAddress(customerReferenceId, request.getAddress());
            }

            customerDataDao.save(customer);
            log.info("updated customer details with customer id: {}", customerReferenceId);

            return customer;
        } catch(Exception e) {
            log.error("received error message while updating the customer data: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public CustomerEntity fetchCustomerDetails(String customerReferenceId) {

        try {
            CustomerEntity customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

            if (ObjectUtils.isEmpty(customer)) {
                log.info("no customer found with customer id:{}", customerReferenceId);

                throw new InvalidException("invalid customer reference id!");
            }

            return customer;
        } catch(Exception e) {
            log.error("received error message: {} while fetching customer details for id: {}", e.getMessage(), customerReferenceId);
            throw e;
        }
    }
}
