package com.befoodly.be.service;

import com.befoodly.be.model.request.CookCreateRequest;

public interface CookService {
    void createCookForVendor (CookCreateRequest request);
}
