package com.befoodly.be.service.impl;

import com.befoodly.be.dao.AddressDataDao;
import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.entity.AddressEntity;
import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.exception.throwable.DuplicationException;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.request.AddressCreateRequest;
import com.befoodly.be.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDataDao addressDataDao;

    private final CustomerDataDao customerDataDao;

    @Override
    public String addAddress(String customerReferenceId, AddressCreateRequest request) {

        try {

            if (isCustomerExist(customerReferenceId)) {
                List<AddressEntity> addressData = addressDataDao.findAddressByCustomerId(customerReferenceId);

                AddressEntity newAddress = AddressEntity.builder()
                        .referenceId(UUID.randomUUID().toString())
                        .customerReferenceId(customerReferenceId)
                        .title(request.getTitle())
                        .phoneNumber(request.getPhoneNumber())
                        .addressFirst(request.getAddressFirst())
                        .pinCode(request.getPinCode())
                        .city(request.getCity())
                        .state(request.getState())
                        .build();

                Optional<AddressEntity> existingAddress = addressData.stream()
                        .filter(address -> address.getTitle().equals(request.getTitle()))
                        .findFirst();

                if (existingAddress.isEmpty()) {
                    addressDataDao.save(newAddress);
                    log.info("saved the new address for customer id: {}", customerReferenceId);

                    return newAddress.getReferenceId();
                }

                throw new DuplicationException("Already exist address with same title for customer");
            }

            throw new InvalidException("customer reference id is invalid!");

        } catch(Exception e) {
            log.error("received following error message:{} while adding address for customerId:{}", e.getMessage(), customerReferenceId);
            throw e;
        }
    }

    private Boolean isCustomerExist(String customerReferenceId) {
        CustomerEntity customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

        if (ObjectUtils.isEmpty(customer)) {
            return false;
        }

        return true;
    }
}