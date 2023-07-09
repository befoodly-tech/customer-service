package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.ProductDataDao;
import com.befoodly.be.entity.ProductEntity;
import com.befoodly.be.model.enums.ProductStatus;
import com.befoodly.be.repository.ProductDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductDataDaoImpl implements ProductDataDao {

    private final ProductDataRepository productDataRepository;

    @Override
    public void save(@NonNull ProductEntity productEntity) {
        productDataRepository.saveAndFlush(productEntity);
    }

    @Override
    public List<ProductEntity> findAllActiveProducts() {
        return productDataRepository.findByStatus(ProductStatus.ACTIVE);
    }


}
