package com.befoodly.be.service;

import com.befoodly.be.model.request.VendorRequest;
import com.befoodly.be.model.response.VendorResponse;

public interface VendorService {
    void createNewVendor(VendorRequest request);
    VendorResponse fetchVendorData(Long id);
}
