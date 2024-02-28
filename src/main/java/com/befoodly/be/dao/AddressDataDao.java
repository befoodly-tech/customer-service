package com.befoodly.be.dao;

import com.befoodly.be.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDataDao {
    void save(Address address);
    List<Address> findAddressByCustomerId(String customerId);
    Optional<Address> findAddressByCustomerIdAndTitle(String customerId, String title);
    void deleteAddress(String customerId, String title);
    Optional<Address> findAddressById(Long id);
}
