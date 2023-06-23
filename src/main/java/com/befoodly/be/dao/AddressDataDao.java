package com.befoodly.be.dao;

import com.befoodly.be.entity.AddressEntity;

import java.util.List;
import java.util.Optional;

public interface AddressDataDao {
    void save(AddressEntity addressEntity);

    List<AddressEntity> findAddressByCustomerId(String customerId);

    Optional<AddressEntity> findAddressByCustomerIdAndTitle(String customerId, String title);

    void deleteAddress(String customerId, String title);
}
