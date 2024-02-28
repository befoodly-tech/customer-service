package com.befoodly.be.service.impl;

import com.befoodly.be.dao.CustomerDataDao;
import com.befoodly.be.entity.Address;
import com.befoodly.be.entity.Customer;
import com.befoodly.be.exception.throwable.DuplicationException;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.request.AddressCreateRequest;
import com.befoodly.be.repository.AddressRepository;
import com.befoodly.be.service.AddressService;
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

    private final AddressRepository addressRepository;
    private final CustomerDataDao customerDataDao;

    private static String phoneNumber;

    @Override
    public Address addAddress(String customerReferenceId, AddressCreateRequest request) {

        try {

            if (isCustomerExist(customerReferenceId)) {
                List<Address> addressData = addressRepository.findByCustomerReferenceId(customerReferenceId);

                Address newAddress = Address.builder()
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

                Optional<Address> existingAddress = addressData.stream()
                        .filter(address -> address.getTitle().equals(request.getTitle()))
                        .findFirst();

                if (existingAddress.isEmpty()) {
                    addressRepository.save(newAddress);
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
    public Address editAddress(String customerReferenceId, String title, AddressCreateRequest request) {
        try {
            Customer customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

            if (ObjectUtils.isNotEmpty(customer)) {
                Optional<Address> address = addressRepository.findByCustomerReferenceIdAndTitle(customerReferenceId, title);

                if (address.isEmpty()) {
                    log.info("Address not found for title: {}, customerId: {}", title, customerReferenceId);

                    throw new InvalidException("Address with title not found for customer!");
                }

                Address currentAddress = address.get();
                currentAddress.setTitle(request.getTitle());
                currentAddress.setAddressFirst(request.getAddressFirst());
                currentAddress.setAddressSecond(request.getAddressSecond());
                currentAddress.setPinCode(request.getPinCode());
                currentAddress.setCity(request.getCity());
                currentAddress.setState(request.getState());

//                customer.setAddress(CommonUtils.convertToOneLineAddress(request));

                addressRepository.save(currentAddress);
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
                addressRepository.deleteByCustomerReferenceIdAndTitle(customerReferenceId, title);
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
    public List<Address> fetchAddressList(String customerReferenceId) {
        try {
            List<Address> addressList = addressRepository.findByCustomerReferenceId(customerReferenceId);

            if (addressList.isEmpty()) {
                log.info("No Address Data present for customerId: {}", customerReferenceId);
                throw new InvalidException("No Address Data Present!");
            }

            log.info("Fetched the list of address data for customer: {}", customerReferenceId);
            return addressList;
        } catch (Exception e) {
            log.error("Failed to fetch the list of addresses for customer id: {}, error: {}", customerReferenceId, e.getMessage());
            throw e;
        }
    }

    private Boolean isCustomerExist(String customerReferenceId) {
        Customer customer = customerDataDao.findCustomerByReferenceId(customerReferenceId);

        if (ObjectUtils.isEmpty(customer)) {
            return false;
        }

        this.phoneNumber = customer.getPhoneNumber();
        return true;
    }
}