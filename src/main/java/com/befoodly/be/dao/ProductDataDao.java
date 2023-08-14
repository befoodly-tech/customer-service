package com.befoodly.be.dao;

import com.befoodly.be.entity.ProductEntity;

import java.util.List;

public interface ProductDataDao {
    void save(ProductEntity productEntity);
    List<ProductEntity> findAllActiveProducts();
    List<ProductEntity> findActiveProductByVendorId(Long vendorId);
}
