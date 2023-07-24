package com.befoodly.be.dao;

import com.befoodly.be.entity.VendorEntity;

import java.util.Optional;

public interface VendorDataDao {
    void save(VendorEntity vendorEntity);
    Optional<VendorEntity> getVendorDataById(Long id);
}
