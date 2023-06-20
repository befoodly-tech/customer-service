package com.befoodly.be.dao;

import com.befoodly.be.entity.AddressEntity;

import java.util.List;

public interface AddressDataDao {
    void save(AddressEntity addressEntity);

    List<AddressEntity> findAddressByCustomerId(String customerId);
}
