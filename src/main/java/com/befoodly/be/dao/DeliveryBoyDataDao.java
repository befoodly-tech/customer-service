package com.befoodly.be.dao;

import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.model.enums.DeliveryBoyStatus;

import java.util.Optional;

public interface DeliveryBoyDataDao {
    void save (DeliveryBoyEntity deliveryBoyEntity);
    Optional<DeliveryBoyEntity> fetchAvailableDeliveryBoy();
}