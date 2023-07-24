package com.befoodly.be.service;

import com.befoodly.be.model.request.VendorRequest;
import com.befoodly.be.model.response.VendorResponse;

import java.util.Optional;

public interface VendorService {
    void createNewVendor(VendorRequest request);
    VendorResponse fetchVendorData(Long id);
}
