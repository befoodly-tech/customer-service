package com.befoodly.be.service;

import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.model.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    List<DeliveryEntity> fetchDeliveryDetails(String customerReferenceId);
}
