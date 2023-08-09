package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.DeliveryBoyDataDao;
import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.repository.DeliveryBoyDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryBoyDataDaoImpl implements DeliveryBoyDataDao {

    private final DeliveryBoyDataRepository deliveryBoyDataRepository;

    @Override
    public void save(@NonNull DeliveryBoyEntity deliveryBoyEntity) {
        deliveryBoyDataRepository.saveAndFlush(deliveryBoyEntity);
    }
}
