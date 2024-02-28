package com.befoodly.be.service;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.request.GenerateOtpRequest;
import com.befoodly.be.model.request.RefreshTokenRequest;
import com.befoodly.be.model.request.VerifyOtpRequest;
import com.befoodly.be.model.response.GenerateOtpResponse;
import com.befoodly.be.model.response.RefreshTokenResponse;
import com.befoodly.be.model.response.VerifyOtpResponse;

public interface LoginService {
    GenerateOtpResponse generateOtp(GenerateOtpRequest request, AppPlatform appPlatform);
    VerifyOtpResponse verifyOtp(VerifyOtpRequest request, AppPlatform appPlatform);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request, AppPlatform appPlatform);
    String logoutUser(String sessionToken, AppPlatform appPlatform);
}
