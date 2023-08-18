package com.befoodly.be.repository;

import com.befoodly.be.entity.ProductEntity;
import com.befoodly.be.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDataRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByStatus(ProductStatus status);

    List<ProductEntity> findByStatusAndVendorId(ProductStatus status, Long vendorId);
}
