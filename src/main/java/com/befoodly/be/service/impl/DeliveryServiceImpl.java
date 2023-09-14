package com.befoodly.be.service.impl;

import com.befoodly.be.dao.AddressDataDao;
import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.entity.AddressEntity;
import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.DeliveryManData;
import com.befoodly.be.model.response.DeliveryResponse;
import com.befoodly.be.service.DeliveryBoyService;
import com.befoodly.be.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryDataDao deliveryDataDao;

    private final AddressDataDao addressDataDao;

    private final DeliveryBoyService deliveryBoyService;

    @Override
    public List<DeliveryResponse> fetchDeliveryDetails(String customerReferenceId) {

        try {
            List<DeliveryEntity> currentPlacedDeliveryDetails = deliveryDataDao.fetchPendingDeliveryDetails(customerReferenceId);

            if (currentPlacedDeliveryDetails.isEmpty()) {
                log.info("no order placed by customer id: {}", customerReferenceId);
                throw new InvalidException("No Placed Order id found for the customer!");
            }

            List<DeliveryResponse> currentDeliveryDetails = currentPlacedDeliveryDetails.stream()
                    .map(deliveryEntity -> mapDeliveryEntityToDeliveryResponse(deliveryEntity))
                    .collect(Collectors.toList());

            log.info("Successfully! fetched the pending delivery order data for customer: {}", customerReferenceId);
            return currentDeliveryDetails;
        } catch (Exception e) {
            log.error("failed to fetch the delivery details for customer: {} with error: {}", customerReferenceId,
                    e.getMessage());
            throw e;
        }
    }

    private DeliveryResponse mapDeliveryEntityToDeliveryResponse(DeliveryEntity deliveryEntity) {
        Optional<AddressEntity> addressEntity = addressDataDao.findAddressById(deliveryEntity.getAddressId());
        DeliveryBoyEntity deliveryBoy = deliveryBoyService.fetchAvailableDeliveryBoy();

        return DeliveryResponse.builder()
                .id(deliveryEntity.getId())
                .orderId(deliveryEntity.getOrderId())
                .finalCost(deliveryEntity.getFinalCost())
                .deliveryCost(deliveryEntity.getDeliveryCost())
                .discountCost(deliveryEntity.getDiscountAmount())
                .status(deliveryEntity.getStatus())
                .deliveryManData(DeliveryManData.builder()
                        .name(deliveryBoy.getName())
                        .phoneNumber(deliveryBoy.getPhoneNumber())
                        .build())
                .deliveryAddress(addressEntity.get())
                .deliveryTime(deliveryEntity.getDeliveryTime())
                .description(deliveryEntity.getDescription())
                .build();
    }

}
