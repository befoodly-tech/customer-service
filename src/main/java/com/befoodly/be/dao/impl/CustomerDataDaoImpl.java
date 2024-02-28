package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.entity.Customer;
import com.befoodly.be.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerDataDaoImpl implements CustomerDataDao {

    private final CustomerRepository customerRepository;

    @Override
    public void save(@NonNull Customer customer) {
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public Optional<Customer> findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findCustomerByReferenceId(String customerReferenceId) {
        Optional<Customer> customer = customerRepository.findByReferenceId(customerReferenceId);

        if (customer.isEmpty()) {
            return null;
        }

        return customer.get();
    }
}
