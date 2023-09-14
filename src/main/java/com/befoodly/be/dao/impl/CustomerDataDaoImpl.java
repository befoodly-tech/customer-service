package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.repository.CustomerDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerDataDaoImpl implements CustomerDataDao {

    private final CustomerDataRepository customerDataRepository;

    @Override
    public void save(@NonNull CustomerEntity customerEntity) {
        customerDataRepository.saveAndFlush(customerEntity);
    }

    @Override
    public Optional<CustomerEntity> findCustomerByPhoneNumber(String phoneNumber) {
        return customerDataRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<CustomerEntity> findCustomerByEmail(String email) {
        return customerDataRepository.findByEmail(email);
    }

    @Override
    public CustomerEntity findCustomerByReferenceId(String customerReferenceId) {
        Optional<CustomerEntity> customer = customerDataRepository.findByReferenceId(customerReferenceId);

        if (customer.isEmpty()) {
            return null;
        }

        return customer.get();
    }
}
