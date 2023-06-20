package com.befoodly.be.service;

import com.befoodly.be.model.request.AddressCreateRequest;

public interface AddressService {
    String addAddress(String customerReferenceId, AddressCreateRequest request);
}
