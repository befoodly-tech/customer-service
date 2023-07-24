package com.befoodly.be.service.impl;

import com.befoodly.be.dao.ProductDataDao;
import com.befoodly.be.entity.ProductEntity;
import com.befoodly.be.model.Feedback;
import com.befoodly.be.model.ProductProvider;
import com.befoodly.be.model.enums.ProductStatus;
import com.befoodly.be.model.request.ProductCreateRequest;
import com.befoodly.be.model.response.ProductDataResponse;
import com.befoodly.be.service.ProductService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductDataDao productDataDao;

    @Override
    public void createNewProduct(ProductCreateRequest request) {

        try {
            String getReferenceId = UUID.randomUUID().toString();

            ProductEntity entity = ProductEntity.builder()
                    .referenceId(getReferenceId)
                    .title(request.getTitle())
                    .imgUrl(request.getImgUrl())
                    .description(request.getDescription())
                    .orderNo(request.getOrderNo())
                    .price(request.getPrice())
                    .acceptingTime(request.getAcceptingTime())
                    .deliveryTime(request.getDeliveryTime())
                    .status(ProductStatus.ACTIVE)
                    .feedback(JacksonUtils.objectToString(request.getFeedback()))
                    .vendorId(request.getVendorId())
                    .providerData(JacksonUtils.objectToString(request.getProviderData()))
                    .build();

            productDataDao.save(entity);
            log.info("successfully created the product for provider with id: {}", getReferenceId);

        } catch(Exception e) {
            log.error("failed to create the product with error msg: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProductDataResponse> fetchAllActiveProducts() {

        try {
            List<ProductEntity> productEntities = productDataDao.findAllActiveProducts();
            log.info("fetched all products list!");

            List<ProductDataResponse> activeProductList = productEntities.stream()
                    .map(productEntity -> ProductDataResponse.builder()
                            .id(productEntity.getId())
                            .referenceId(productEntity.getReferenceId())
                            .title(productEntity.getTitle())
                            .description(productEntity.getDescription())
                            .imgUrl(productEntity.getImgUrl())
                            .price(productEntity.getPrice())
                            .orderNo(productEntity.getOrderNo())
                            .acceptingTime(productEntity.getAcceptingTime())
                            .deliveryTime(productEntity.getDeliveryTime())
                            .feedback(JacksonUtils.stringToObject(productEntity.getFeedback(), Feedback.class))
                            .vendorId(productEntity.getVendorId())
                            .providerData(JacksonUtils.stringToObject(productEntity.getProviderData(), ProductProvider.class))
                            .createdAt(productEntity.getCreatedAt())
                            .updatedAt(productEntity.getUpdatedAt())
                            .build())
                    .collect(Collectors.toList());

            return activeProductList;
        } catch (Exception e) {
            log.error("received error message: {} while fetching all active products", e.getMessage());
            throw e;
        }
    }
}
