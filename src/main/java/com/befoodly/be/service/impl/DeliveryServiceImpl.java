package com.befoodly.be.service.impl;

import com.befoodly.be.dao.AddressDataDao;
import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.entity.Address;
import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.DeliveryManData;
import com.befoodly.be.model.TimeSlot;
import com.befoodly.be.model.constant.TimeSlotConstants;
import com.befoodly.be.model.response.DeliveryResponse;
import com.befoodly.be.repository.AddressRepository;
import com.befoodly.be.service.DeliveryBoyService;
import com.befoodly.be.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private static final Integer maxSlotCount = 1;

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

    @Override
    public List<TimeSlot> fetchAvailableTimeSlots() {
        try {
            List<DeliveryEntity> pendingDeliveries = deliveryDataDao.fetchAllPendingDeliveryDetails();

            List<TimeSlot> timeSlotsAfterAnHour = TimeSlotConstants.timeSlots.stream()
                    .filter(timeSlot -> isTimeDifferenceMoreThanOneHour(timeSlot))
                    .collect(Collectors.toList());

            if (pendingDeliveries.isEmpty()) {
                return timeSlotsAfterAnHour;
            }

            List<LocalDateTime> pendingOrderDeliveryTimes = pendingDeliveries.stream()
                    .map(deliveryEntity -> deliveryEntity.getDeliveryTime())
                    .collect(Collectors.toList());

            if (timeSlotsAfterAnHour.isEmpty()) {
                return List.of();
            }

            List<TimeSlot> availableTimeSlots = timeSlotsAfterAnHour.stream()
                    .filter(timeSlot -> isSlotAvailable(timeSlot, pendingOrderDeliveryTimes))
                    .collect(Collectors.toList());

            return availableTimeSlots;

        } catch (Exception e) {
            log.error("failed to fetch available time slots with error: {}", e.getMessage());
            throw e;
        }
    }

    private boolean isTimeDifferenceMoreThanOneHour (TimeSlot timeSlot) {
        LocalTime currentLocalTime = LocalTime.now();
        LocalTime slotLocalTime = LocalTime.of(timeSlot.getHour(), timeSlot.getMinutes());

        Duration duration = Duration.between(currentLocalTime, slotLocalTime);

        long hourDiff = duration.toHours();
        
        return hourDiff >= 1;
    }

    private boolean isSlotAvailable (TimeSlot timeSlot, List<LocalDateTime> deliveryTime) {
        List<LocalDateTime> matchedTimeSlots = deliveryTime.stream()
                .filter(localDateTime -> timeSlot.getHour() == localDateTime.getHour() && timeSlot.getMinutes() == localDateTime.getMinute())
                .toList();

        return matchedTimeSlots.size() < maxSlotCount;
    }

    private DeliveryResponse mapDeliveryEntityToDeliveryResponse(DeliveryEntity deliveryEntity) {
        Optional<Address> addressEntity = addressDataDao.findAddressById(deliveryEntity.getAddressId());
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
                .orderTime(deliveryEntity.getCreatedAt())
                .description(deliveryEntity.getDescription())
                .build();
    }

}
