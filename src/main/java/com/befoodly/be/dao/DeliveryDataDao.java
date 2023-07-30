package com.befoodly.be.dao;

import com.befoodly.be.entity.DeliveryEntity;

import java.util.List;

public interface DeliveryDataDao {
    void save(DeliveryEntity deliveryEntity);
    List<DeliveryEntity> fetchPendingDeliveryDetails(String customerReferenceId);
}
