package com.befoodly.be.utils;

import com.befoodly.be.model.request.AddressCreateRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class CommonUtils {

    public static String GenerateOtp() {
        Random random = new Random();

        return String.format("%04d", random.nextInt(10000));
    }

    public static String convertToOneLineAddress (AddressCreateRequest request) {
        String currentAddress = request.getAddressFirst();

        if (StringUtils.isNotEmpty(request.getAddressSecond())) {
            currentAddress += ", " + request.getAddressSecond();
        }

        currentAddress += ", " + request.getCity() + ", " + request.getPinCode();
        currentAddress += ", " + request.getState();

        return currentAddress;
    }
}
