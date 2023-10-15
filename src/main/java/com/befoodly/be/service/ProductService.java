package com.befoodly.be.service;

import com.befoodly.be.model.request.ProductCreateRequest;
import com.befoodly.be.model.request.ProductEditRequest;
import com.befoodly.be.model.response.ProductDataResponse;

import java.util.List;

public interface ProductService {
    void createNewProduct(ProductCreateRequest request);
    List<ProductDataResponse> fetchAllActiveProducts();
    List<ProductDataResponse> fetchPopularActiveProducts();
    List<ProductDataResponse> fetchActiveProductsByVendor(Long vendorId);
    void updateProductDetails(Long productId, ProductEditRequest request);
}
