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
    public CustomerEntity findCustomerByPhoneNumber(String phoneNumber) {

        Optional<CustomerEntity> customer = customerDataRepository.findByPhoneNumber(phoneNumber);

        return customer.get();
    }
}
