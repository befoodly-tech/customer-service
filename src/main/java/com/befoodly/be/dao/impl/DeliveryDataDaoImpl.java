package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.model.enums.DeliveryStatus;
import com.befoodly.be.repository.DeliveryDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DeliveryDataDaoImpl implements DeliveryDataDao {

    private final DeliveryDataRepository deliveryDataRepository;

    @Override
    public void save(@NonNull DeliveryEntity deliveryEntity) {
        deliveryDataRepository.saveAndFlush(deliveryEntity);
    }

    @Override
    public List<DeliveryEntity> fetchPendingDeliveryDetails(String customerReferenceId) {
        return deliveryDataRepository.findAllPendingDeliveryByCustomerId(customerReferenceId);
    }

    @Override
    public List<DeliveryEntity> fetchAllPendingDeliveryDetails() {
        return deliveryDataRepository.findAllPendingDelivery();
    }
}
