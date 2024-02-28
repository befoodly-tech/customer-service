package com.befoodly.be.service;

import com.befoodly.be.entity.Address;
import com.befoodly.be.model.request.AddressCreateRequest;

import java.util.List;

public interface AddressService {
    Address addAddress(String customerReferenceId, AddressCreateRequest request);
    Address editAddress(String customerReferenceId, String title, AddressCreateRequest request);
    void deleteAddress(String customerReferenceId, String title);
    List<Address> fetchAddressList(String customerReferenceId);
}
