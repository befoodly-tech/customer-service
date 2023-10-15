package com.befoodly.be.dao;

import com.befoodly.be.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDataDao {
    void save(ProductEntity productEntity);
    List<ProductEntity> findAllActiveProducts();
    List<ProductEntity> findActiveProductByVendorId(Long vendorId);
    Optional<ProductEntity> findProductByProductId(Long productId);
}
