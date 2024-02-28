package com.befoodly.be.controller;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.request.GenerateOtpRequest;
import com.befoodly.be.model.request.RefreshTokenRequest;
import com.befoodly.be.model.request.VerifyOtpRequest;
import com.befoodly.be.model.response.*;
import com.befoodly.be.service.LoginService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.befoodly.be.model.constant.CommonConstants.PHONE_NUMBER;
import static com.befoodly.be.model.constant.CommonConstants.SESSION_TOKEN;
import static com.befoodly.be.model.constant.HeaderConstants.HEADER_PLATFORM;

@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private final LoginService loginService;

    @GetMapping(value = "/otp-generate")
    public ResponseEntity<GenericResponse<?>> generateOtp(
            @RequestBody @NonNull GenerateOtpRequest generateOtpRequest,
            @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform
    ) {
        GenerateOtpResponse generateOtpResponse = loginService.generateOtp(generateOtpRequest, appPlatform);
        return ResponseEntity.ok(GenericResponse.<GenerateOtpResponse>builder()
            .data(generateOtpResponse)
            .statusCode(HttpStatus.OK.value())
            .build());
    }

    @PutMapping(value = "/otp-verify")
    public ResponseEntity<GenericResponse<?>> verifyOtp(
            @RequestBody VerifyOtpRequest verifyOtpRequest,
            @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform
    ) {
        VerifyOtpResponse verifyOtpResponse = loginService.verifyOtp(verifyOtpRequest, appPlatform);
        return ResponseEntity.ok(GenericResponse.<VerifyOtpResponse>builder()
            .data(verifyOtpResponse)
            .statusCode(HttpStatus.OK.value())
            .build());
    }

    @GetMapping(value = "/resend-otp")
    public ResponseEntity<GenericResponse<?>> resendOtp(
            @RequestBody @NonNull GenerateOtpRequest generateOtpRequest,
            @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform
    ) {
        GenerateOtpResponse generateOtpResponse = loginService.generateOtp(generateOtpRequest, appPlatform);
        return ResponseEntity.ok(GenericResponse.<GenerateOtpResponse>builder()
            .data(generateOtpResponse)
            .statusCode(HttpStatus.OK.value())
            .build());
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<GenericResponse<?>> refreshToken(
            @RequestBody @NonNull RefreshTokenRequest refreshTokenRequest,
            @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform
    ) {
        RefreshTokenResponse refreshTokenResponse = loginService.refreshToken(refreshTokenRequest, appPlatform);
        return ResponseEntity.ok(GenericResponse.<RefreshTokenResponse>builder()
                .data(refreshTokenResponse)
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
