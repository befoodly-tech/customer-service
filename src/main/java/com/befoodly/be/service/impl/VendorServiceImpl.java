package com.befoodly.be.service.impl;

import com.befoodly.be.dao.VendorDataDao;
import com.befoodly.be.entity.VendorEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.Feedback;
import com.befoodly.be.model.enums.VendorStatus;
import com.befoodly.be.model.request.VendorRequest;
import com.befoodly.be.model.response.CookProfileResponse;
import com.befoodly.be.model.response.ProductDataResponse;
import com.befoodly.be.model.response.VendorResponse;
import com.befoodly.be.service.CookService;
import com.befoodly.be.service.ProductService;
import com.befoodly.be.service.VendorService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class VendorServiceImpl implements VendorService {

    private final VendorDataDao vendorDataDao;

    private final CookService cookService;

    private final ProductService productService;

    @Override
    public void createNewVendor(VendorRequest request) {

        try {
            String getReferenceId = UUID.randomUUID().toString();

            VendorEntity vendorEntity = VendorEntity.builder()
                    .referenceId(getReferenceId)
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .description(request.getDescription())
                    .imgUrl(request.getImgUrl())
                    .status(VendorStatus.ACTIVE)
                    .cookList(JacksonUtils.objectToString(request.getCookList()))
                    .feedback(JacksonUtils.objectToString(request.getFeedback()))
                    .build();

            vendorDataDao.save(vendorEntity);
            log.info("successfully created vendor with reference id: {}", getReferenceId);

        } catch(Exception e) {
            log.error("failed to create the vendor with error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public VendorResponse fetchVendorData(Long id) {

        try {
            Optional<VendorEntity> optionalVendorData = vendorDataDao.getVendorDataById(id);
            log.info("successfully! fetched vendor data for id: {}", id);

            if (optionalVendorData.isEmpty()) {
                log.info("no vendor data found for id: {}", id);

                throw new InvalidException("invalid id was passed, no data found!");
            }

            List<CookProfileResponse> cookDataList = cookService.fetchAllCooksForVendor(id);
            List<ProductDataResponse> activeProductList = productService.fetchActiveProductsByVendor(id);

            VendorEntity vendorData = optionalVendorData.get();
            VendorResponse vendorResponseData = VendorResponse.builder()
                    .id(vendorData.getId())
                    .referenceId(vendorData.getReferenceId())
                    .name(vendorData.getName())
                    .email(vendorData.getEmail())
                    .imgUrl(vendorData.getImgUrl())
                    .description(vendorData.getDescription())
                    .address(vendorData.getAddress())
                    .status(vendorData.getStatus())
                    .phoneNumber(vendorData.getPhoneNumber())
                    .cookList(cookDataList)
                    .productList(activeProductList)
                    .feedback(JacksonUtils.stringToObject(vendorData.getFeedback(), Feedback.class))
                    .build();

            return vendorResponseData;

        } catch(Exception e) {
            log.error("failed to fetch the vendor details with error: {} for id: {}", e.getMessage(), id);
            throw e;
        }
    }
}
