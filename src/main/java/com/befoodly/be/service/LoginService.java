package com.befoodly.be.service;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.response.EmailLoginResponse;
import com.befoodly.be.model.response.LoginResponse;

public interface LoginService {

    EmailLoginResponse signUpUser(String phoneNumber, AppPlatform appPlatform);

    EmailLoginResponse loginUser(String email, AppPlatform appPlatform);

    String logoutUser(String sessionToken, AppPlatform appPlatform);

    LoginResponse otpVerification(String otp, String phoneNumber, AppPlatform appPlatform);

    String resendOtp(String phoneNumber, AppPlatform appPlatform);

    void editLoginNumber(String phoneNumber, AppPlatform appPlatform);

    Boolean checkExpiryOfSessionToken(String sessionToken, AppPlatform appPlatform);
}
