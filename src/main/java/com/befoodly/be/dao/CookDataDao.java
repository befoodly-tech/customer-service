package com.befoodly.be.dao;

import com.befoodly.be.entity.CookDataEntity;

import java.util.List;

public interface CookDataDao {
    void save (CookDataEntity cookDataEntity);
    List<CookDataEntity> fetchFivePopularCooks (Long orderCounts);
    List<CookDataEntity> fetchAllCooksForVendor (Long vendorId);
}
