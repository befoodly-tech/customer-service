package com.befoodly.be.service.impl;

import com.befoodly.be.dao.DeliveryBoyDataDao;
import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.model.enums.DeliveryBoyStatus;
import com.befoodly.be.model.request.DeliveryBoyCreateRequest;
import com.befoodly.be.service.DeliveryBoyService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class DeliveryBoyServiceImpl implements DeliveryBoyService {

    private final DeliveryBoyDataDao deliveryBoyDataDao;

    @Override
    public void createDeliveryBoyProfile(DeliveryBoyCreateRequest request) {
        try {
            String referenceId = UUID.randomUUID().toString();

            DeliveryBoyEntity entity = DeliveryBoyEntity.builder()
                    .referenceId(referenceId)
                    .name(request.getName())
                    .imgUrl(request.getImgUrl())
                    .description(request.getDescription())
                    .phoneNumber(request.getPhoneNumber())
                    .panNumber(request.getPanNumber())
                    .address(JacksonUtils.objectToString(request.getAddress()))
                    .feedback(JacksonUtils.objectToString(request.getFeedback()))
                    .status(DeliveryBoyStatus.AVAILABLE)
                    .build();

            deliveryBoyDataDao.save(entity);
            log.info("Successfully! saved the new delivery boy details");
        } catch (Exception e) {
            log.error("Received error message: {}, while creating profile for delivery boy with number: {}",
                    e.getMessage(), request.getPhoneNumber());
            throw e;
        }
    }
}
