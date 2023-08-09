package com.befoodly.be.service.impl;

import com.befoodly.be.dao.CookDataDao;
import com.befoodly.be.entity.CookDataEntity;
import com.befoodly.be.model.enums.CookStatus;
import com.befoodly.be.model.request.CookCreateRequest;
import com.befoodly.be.service.CookService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CookServiceImpl implements CookService {

    private final CookDataDao cookDataDao;

    @Override
    public void createCookForVendor(CookCreateRequest request) {

        try {
            String referenceId = UUID.randomUUID().toString();

            CookDataEntity cookDataEntity = CookDataEntity.builder()
                    .referenceId(referenceId)
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .panNumber(request.getPanNumber())
                    .description(request.getDescription())
                    .vendorId(request.getVendorId())
                    .address(JacksonUtils.objectToString(request.getAddress()))
                    .feedback(JacksonUtils.objectToString(request.getFeedback()))
                    .specialities(JacksonUtils.objectToString(request.getSpecialities()))
                    .status(CookStatus.AVAILABLE)
                    .build();

            cookDataDao.save(cookDataEntity);
            log.info("Successfully! saved the data for cook for vendor id: {}", request.getVendorId());
        } catch (Exception e) {
            log.error("received error message: {}, while creating cook profile for vendor id: {}",
                    e.getMessage(), request.getVendorId());
            throw e;
        }
    }
}
