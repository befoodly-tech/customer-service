package com.befoodly.be.service;

import com.befoodly.be.entity.AddressEntity;
import com.befoodly.be.model.request.AddressCreateRequest;

public interface AddressService {
    String addAddress(String customerReferenceId, AddressCreateRequest request);
    AddressEntity editAddress(String customerReferenceId, String title, AddressCreateRequest request);
    void deleteAddress(String customerReferenceId, String title);
}
