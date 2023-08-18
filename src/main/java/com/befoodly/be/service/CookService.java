package com.befoodly.be.service;

import com.befoodly.be.model.request.CookCreateRequest;
import com.befoodly.be.model.response.CookProfileResponse;

import java.util.List;

public interface CookService {
    void createCookForVendor (CookCreateRequest request);
    List<CookProfileResponse> fetchPopularCooks (Long orderCounts);
    List<CookProfileResponse> fetchAllCooksForVendor (Long vendorId);
    CookProfileResponse updateCookOrderCounts (Long id, Long orderCounts);
}
