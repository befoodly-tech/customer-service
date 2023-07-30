package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.dao.OrderDataDao;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.repository.DeliveryDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DeliveryDataDaoImpl implements DeliveryDataDao {

    private final DeliveryDataRepository deliveryDataRepository;

    private final OrderDataDao orderDataDao;

    @Override
    public void save(@NonNull DeliveryEntity deliveryEntity) {
        deliveryDataRepository.saveAndFlush(deliveryEntity);
    }

    @Override
    public List<DeliveryEntity> fetchPendingDeliveryDetails(String customerReferenceId) {
        List<OrderEntity> allPlacedOrderIdentities = orderDataDao.findAllPlacedOrderDetails(customerReferenceId);
        List<DeliveryEntity> allPendingDeliveryDetails = allPlacedOrderIdentities.stream()
                .map(orderEntity -> deliveryDataRepository.findByOrderId(orderEntity.getId()))
                .collect(Collectors.toList());

        return allPendingDeliveryDetails;
    }
}
