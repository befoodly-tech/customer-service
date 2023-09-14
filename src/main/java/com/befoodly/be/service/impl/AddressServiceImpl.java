package com.befoodly.be.service.impl;

import com.befoodly.be.dao.AddressDataDao;
import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.entity.AddressEntity;
import com.befoodly.be.entity.CustomerEntity;
import com.befoodly.be.exception.throwable.DuplicationException;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.request.AddressCreateRequest;
import com.befoodly.be.service.AddressService;
import com.befoodly.be.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDataDao addressDataDao;

    private final CustomerDataDao customerDataDao;

    private static String phoneNumber;

    @Override
    public AddressEntity addAddress(String customerReferenceId, AddressCreateRequest request) {

        try {

            if (isCustomerExist(customerReferenceId)) {
                List<AddressEntity> addressData = addressDataDao.findAddressByCustomerId(customerReferenceId);

                AddressEntity newAddress = AddressEntity.builder()
                        .referenceId(UUID.randomUUID().toString())
                        .customerReferenceId(customerReferenceId)
                        .title(request.getTitle())
                        .phoneNumber(StringUtils.isNotEmpty(request.getPhoneNumber()) ? request.getPhoneNumber() : phoneNumber)
                        .addressFirst(request.getAddressFirst())
                        .addressSecond(StringUtils.isNotEmpty(request.getAddressSecond()) ? request.getAddressSecond() : "")
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

                    return newAddress;
                }

                throw new DuplicationException("Already exist address with same title for customer");
            }

            throw new InvalidException("customer reference id is invalid!");

        } catch(Exception e) {
            log.error("received following error message:{} while adding address for customerId:{}", e.getMessage(), customerReferenceId);
            throw e;
        }
    }

    @Override
    @Transactional
    public AddressEntity editAddress(String customerReferenceId, String title, AddressCreateRequest request) {

        try {
            CustomerEntity customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

            if (ObjectUtils.isNotEmpty(customer)) {
                Optional<AddressEntity> address = addressDataDao.findAddressByCustomerIdAndTitle(customerReferenceId, title);

                if (address.isEmpty()) {
                    log.info("Address not found for title: {}, customerId: {}", title, customerReferenceId);

                    throw new InvalidException("Address with title not found for customer!");
                }

                AddressEntity currentAddress = address.get();
                currentAddress.setTitle(request.getTitle());
                currentAddress.setAddressFirst(request.getAddressFirst());
                currentAddress.setAddressSecond(request.getAddressSecond());
                currentAddress.setPinCode(request.getPinCode());
                currentAddress.setCity(request.getCity());
                currentAddress.setState(request.getState());

                customer.setAddress(CommonUtils.convertToOneLineAddress(request));

                addressDataDao.save(currentAddress);
                customerDataDao.save(customer);
                log.info("successfully updated the address for customerId:{}", customerReferenceId);

                return currentAddress;
            }

            log.info("Customer with reference id: {} is not found", customerReferenceId);

            throw new InvalidException("Customer does not exist!");
        } catch(Exception e) {
            log.error("received an error message:{} while editing the address for customer id:{}", e.getMessage(), customerReferenceId);
            throw e;
        }
    }

    @Override
    public void deleteAddress(String customerReferenceId, String title) {
        try {
            if (isCustomerExist(customerReferenceId)) {
                addressDataDao.deleteAddress(customerReferenceId, title);
                log.info("successfully deleted the address with title: {} for customer Id:{}", title, customerReferenceId);

                return;
            }

            throw new InvalidException("Customer doesn't exist!");
        } catch (Exception e) {
            log.error("received error: {} while deleting the address for customerId: {} with title: {}", e.getMessage(), customerReferenceId, title);
            throw e;
        }
    }

    @Override
    public List<AddressEntity> fetchAddressList(String customerReferenceId) {
        try {
            List<AddressEntity> addressEntityList = addressDataDao.findAddressByCustomerId(customerReferenceId);

            if (addressEntityList.isEmpty()) {
                log.info("No Address Data present for customerId: {}", customerReferenceId);
                throw new InvalidException("No Address Data Present!");
            }

            log.info("Fetched the list of address data for customer: {}", customerReferenceId);
            return addressEntityList;
        } catch (Exception e) {
            log.error("Failed to fetch the list of addresses for customer id: {}, error: {}", customerReferenceId, e.getMessage());
            throw e;
        }
    }

    private Boolean isCustomerExist(String customerReferenceId) {
        CustomerEntity customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

        if (ObjectUtils.isEmpty(customer)) {
            return false;
        }

        this.phoneNumber = customer.getPhoneNumber();
        return true;
    }
}