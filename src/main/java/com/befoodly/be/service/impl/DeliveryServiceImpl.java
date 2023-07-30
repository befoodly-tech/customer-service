package com.befoodly.be.service.impl;

import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.enums.DeliveryStatus;
import com.befoodly.be.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryDataDao deliveryDataDao;

    @Override
    public List<DeliveryEntity> fetchDeliveryDetails(String customerReferenceId) {

        try {
            List<DeliveryEntity> currentPlacedDeliveryDetails = deliveryDataDao.fetchPendingDeliveryDetails(customerReferenceId);

            if (currentPlacedDeliveryDetails.isEmpty()) {
                log.info("no order placed by customer id: {}", customerReferenceId);
                throw new InvalidException("No Placed Order id found for the customer!");
            }

            log.info("Successfully! fetched the pending delivery order data for customer: {}", customerReferenceId);
            return currentPlacedDeliveryDetails;
        } catch (Exception e) {
            log.error("failed to fetch the delivery details for customer: {} with error: {}", customerReferenceId,
                    e.getMessage());
            throw e;
        }
    }
}
