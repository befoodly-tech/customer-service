package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.DeliveryBoyDataDao;
import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.model.enums.DeliveryBoyStatus;
import com.befoodly.be.repository.DeliveryBoyDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DeliveryBoyDataDaoImpl implements DeliveryBoyDataDao {

    private final DeliveryBoyDataRepository deliveryBoyDataRepository;

    @Override
    public void save(@NonNull DeliveryBoyEntity deliveryBoyEntity) {
        deliveryBoyDataRepository.saveAndFlush(deliveryBoyEntity);
    }

    @Override
    public Optional<DeliveryBoyEntity> fetchAvailableDeliveryBoy() {
        List<DeliveryBoyEntity> listOfDeliveryBoys = deliveryBoyDataRepository.findByStatus(DeliveryBoyStatus.AVAILABLE);

        if (listOfDeliveryBoys.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(listOfDeliveryBoys.get(0));
    }
}
