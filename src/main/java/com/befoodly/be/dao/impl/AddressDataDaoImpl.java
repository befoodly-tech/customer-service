package com.befoodly.be.dao.impl;

import com.befoodly.be.entity.Address;
import com.befoodly.be.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AddressDataDaoImpl {

    private final AddressRepository addressRepository;

    public void save(@NonNull Address address) {
        addressRepository.saveAndFlush(address);
    }

    public List<Address> findAddressByCustomerId(String customerId) {
        return addressRepository.findByCustomerReferenceId(customerId);
    }

    public Optional<Address> findAddressByCustomerIdAndTitle(String customerId, String title) {
        return addressRepository.findByCustomerReferenceIdAndTitle(customerId, title);
    }

    @Transactional
    public void deleteAddress(String customerId, String title) {
        addressRepository.deleteByCustomerReferenceIdAndTitle(customerId, title);
    }

    public Optional<Address> findAddressById(Long id) {
        return addressRepository.findById(id);
    }
}
