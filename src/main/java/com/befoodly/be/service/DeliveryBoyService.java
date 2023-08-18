package com.befoodly.be.service;

import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.model.request.DeliveryBoyCreateRequest;

public interface DeliveryBoyService {
    void createDeliveryBoyProfile (DeliveryBoyCreateRequest request);
    DeliveryBoyEntity fetchAvailableDeliveryBoy();
}
