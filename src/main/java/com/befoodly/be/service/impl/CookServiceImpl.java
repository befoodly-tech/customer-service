package com.befoodly.be.service.impl;

import com.befoodly.be.dao.CookDataDao;
import com.befoodly.be.entity.CookDataEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.Feedback;
import com.befoodly.be.model.enums.CookStatus;
import com.befoodly.be.model.request.CookCreateRequest;
import com.befoodly.be.model.response.CookProfileResponse;
import com.befoodly.be.service.CookService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
                    .imgUrl(request.getImgUrl())
                    .phoneNumber(request.getPhoneNumber())
                    .panNumber(request.getPanNumber())
                    .description(request.getDescription())
                    .vendorId(request.getVendorId())
                    .orderCounts(0L)
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

    @Override
    public List<CookProfileResponse> fetchPopularCooks (Long orderCounts) {

        try {
            List<CookDataEntity> cookList = cookDataDao.fetchFivePopularCooks(orderCounts);

            log.info("Successfully! fetched 5 popular cook data!");
            return mapCookEntityListToCookProfileResponse(cookList);
        } catch (Exception e) {
            log.error("Received error message: {}, while fetching popular cook data", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CookProfileResponse> fetchAllCooksForVendor(Long vendorId) {
        try {
            List<CookDataEntity> cookList = cookDataDao.fetchAllCooksForVendor(vendorId);

            log.info("Successfully! fetched all cook data for vendorId: {}", vendorId);
            return mapCookEntityListToCookProfileResponse(cookList);
        } catch (Exception e) {
            log.error("Received error message: {}, while fetching cook data for vendorId: {}",
                    e.getMessage(), vendorId);
            throw e;
        }
    }

    @Override
    @Transactional
    public CookProfileResponse updateCookOrderCounts(Long id, Long orderCounts) {
        try {
            Optional<CookDataEntity> cookEntity = cookDataDao.fetchCookById(id);

            if (cookEntity.isEmpty()) {
                log.info("No cook data found with the id: {}", id);
                throw new InvalidException("No Cook found with Id!");
            }

            CookDataEntity currentCookData = cookEntity.get();
            Long currentCount = currentCookData.getOrderCounts();

            currentCookData.setOrderCounts(currentCount + orderCounts);
            cookDataDao.save(currentCookData);
            log.info("Successfully, updated the order counts for the cook with id: {}", id);

            return mapCookEntityToCookProfileResponse(currentCookData);
        } catch (Exception e) {
            log.error("Failed to update the order count for the cook: {}, with error: {}", id, e.getMessage());
            throw e;
        }
    }

    private List<CookProfileResponse> mapCookEntityListToCookProfileResponse (List<CookDataEntity> cookDataList) {
        if (cookDataList.isEmpty()) {
            return null;
        }

        List<CookProfileResponse> cookProfileResponseList = cookDataList.stream()
                .map(cookDataEntity -> mapCookEntityToCookProfileResponse(cookDataEntity))
                .collect(Collectors.toList());

        return cookProfileResponseList;
    }

    private CookProfileResponse mapCookEntityToCookProfileResponse (CookDataEntity cookDataEntity) {
        return CookProfileResponse.builder()
                .id(cookDataEntity.getId())
                .referenceId(cookDataEntity.getReferenceId())
                .name(cookDataEntity.getName())
                .imgUrl(cookDataEntity.getImgUrl())
                .description(cookDataEntity.getDescription())
                .vendorId(cookDataEntity.getVendorId())
                .orderCounts(cookDataEntity.getOrderCounts())
                .specialities(JacksonUtils.stringToListObject(cookDataEntity.getSpecialities(), String.class))
                .feedback(JacksonUtils.stringToObject(cookDataEntity.getFeedback(), Feedback.class))
                .build();
    }
}
