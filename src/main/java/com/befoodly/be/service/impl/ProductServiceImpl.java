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

import java.util.ArrayList;
import java.util.Comparator;
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
                    .map(productEntity -> mapProductEntityToProductDataResponse(productEntity))
                    .collect(Collectors.toList());

            return activeProductList;
        } catch (Exception e) {
            log.error("received error message: {} while fetching all active products", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProductDataResponse> fetchPopularActiveProducts() {
        try {
            List<ProductDataResponse> fetchedAllActiveProducts = fetchAllActiveProducts();
            fetchedAllActiveProducts.sort(Comparator.comparing(ProductDataResponse::getOrderNo));

            log.info("Successfully! fetched the trending product lists");

            if (fetchedAllActiveProducts.size() <= 3) {
                return fetchedAllActiveProducts;
            }

            List<ProductDataResponse> filterPopularProducts = new ArrayList<ProductDataResponse>();

            for (int i = 0; i<3; i++) {
                filterPopularProducts.add(fetchedAllActiveProducts.get(i));
            }

            return filterPopularProducts;
        } catch (Exception e) {
            log.error("Failed to fetch the list of trending products: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<ProductDataResponse> fetchActiveProductsByVendor(Long vendorId) {
        try {
            List<ProductEntity> productEntities = productDataDao.findActiveProductByVendorId(vendorId);
            log.info("Successfully! fetched the product data for vendor id: {}", vendorId);

            List<ProductDataResponse> activeProductList = productEntities.stream()
                    .map(productEntity -> mapProductEntityToProductDataResponse(productEntity))
                    .collect(Collectors.toList());

            return activeProductList;
        } catch (Exception e) {
            log.error("Received error: {} while fetch product data for vendor id: {}", e.getMessage(), vendorId);
            throw e;
        }
    }

    private ProductDataResponse mapProductEntityToProductDataResponse(ProductEntity productEntity) {
        return ProductDataResponse.builder()
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
                .build();
    }
}
