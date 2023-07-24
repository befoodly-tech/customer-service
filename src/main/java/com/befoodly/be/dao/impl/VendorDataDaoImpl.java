package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.VendorDataDao;
import com.befoodly.be.entity.VendorEntity;
import com.befoodly.be.repository.VendorDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class VendorDataDaoImpl implements VendorDataDao {

    private final VendorDataRepository vendorDataRepository;

    @Override
    public void save(@NonNull VendorEntity vendorEntity) {
        vendorDataRepository.saveAndFlush(vendorEntity);
    }

    @Override
    public Optional<VendorEntity> getVendorDataById(Long id) {
        return vendorDataRepository.findById(id);
    }
}
