package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.CookDataDao;
import com.befoodly.be.entity.CookDataEntity;
import com.befoodly.be.repository.CookDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CookDataDaoImpl implements CookDataDao {

    private final CookDataRepository cookDataRepository;

    @Override
    public void save(@NonNull CookDataEntity cookDataEntity) {
        cookDataRepository.saveAndFlush(cookDataEntity);
    }

    @Override
    public List<CookDataEntity> fetchFivePopularCooks(Long orderCounts) {
        return cookDataRepository.findPopularCooks(orderCounts);
    }

    @Override
    public List<CookDataEntity> fetchAllCooksForVendor(Long vendorId) {
        return cookDataRepository.findByVendorId(vendorId);
    }
}
