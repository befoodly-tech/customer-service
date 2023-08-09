package com.befoodly.be.service;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.response.LoginResponse;

public interface LoginService {

    String loginUser(String phoneNumber, AppPlatform appPlatform);

    String logoutUser(String phoneNumber, AppPlatform appPlatform);

    LoginResponse otpVerification(String otp, String phoneNumber, AppPlatform appPlatform);

    String resendOtp(String phoneNumber, AppPlatform appPlatform);

    void editLoginNumber(String phoneNumber, AppPlatform appPlatform);
}
