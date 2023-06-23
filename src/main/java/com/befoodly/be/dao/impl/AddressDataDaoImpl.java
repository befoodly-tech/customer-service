package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.AddressDataDao;
import com.befoodly.be.entity.AddressEntity;
import com.befoodly.be.repository.AddressDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AddressDataDaoImpl implements AddressDataDao {

    private final AddressDataRepository addressDataRepository;

    @Override
    public void save(@NonNull AddressEntity addressEntity) {
        addressDataRepository.saveAndFlush(addressEntity);
    }

    @Override
    public List<AddressEntity> findAddressByCustomerId(String customerId) {
        return addressDataRepository.findByCustomerReferenceId(customerId);
    }

    @Override
    public Optional<AddressEntity> findAddressByCustomerIdAndTitle(String customerId, String title) {
        return addressDataRepository.findByCustomerReferenceIdAndTitle(customerId, title);
    }

    @Override
    @Transactional
    public void deleteAddress(String customerId, String title) {
        addressDataRepository.deleteByCustomerReferenceIdAndTitle(customerId, title);
    }
}
