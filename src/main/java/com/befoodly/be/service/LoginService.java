package com.befoodly.be.service;

import com.befoodly.be.model.enums.AppPlatform;

public interface LoginService {

    String loginUser(String phoneNumber, AppPlatform appPlatform);

    String logoutUser(String phoneNumber, AppPlatform appPlatform);

    String otpVerification(String otp, String referenceId);

    String resendOtp(String referenceId);

    void editLoginNumber(String referenceId);
}
