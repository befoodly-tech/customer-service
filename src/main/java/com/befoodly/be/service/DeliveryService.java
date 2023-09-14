package com.befoodly.be.service;

import com.befoodly.be.model.response.DeliveryResponse;

import java.util.List;

public interface DeliveryService {
    List<DeliveryResponse> fetchDeliveryDetails(String customerReferenceId);
}
