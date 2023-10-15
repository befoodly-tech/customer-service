package com.befoodly.be.controller;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.response.EmailLoginResponse;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.model.response.LoginResponse;
import com.befoodly.be.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.befoodly.be.model.constant.CommonConstants.PHONE_NUMBER;
import static com.befoodly.be.model.constant.CommonConstants.SESSION_TOKEN;
import static com.befoodly.be.model.constant.HeaderConstants.HEADER_PLATFORM;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @PostMapping(value = "/signup/{phoneNumber}")
    public ResponseEntity<GenericResponse<EmailLoginResponse>> signUpUser(@PathVariable(value = PHONE_NUMBER) String phoneNumber,
                                                             @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform) {
        EmailLoginResponse signUpResponse = loginService.signUpUser(phoneNumber, appPlatform);
        return ResponseEntity.ok(GenericResponse.<EmailLoginResponse>builder()
                .data(signUpResponse)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<GenericResponse<EmailLoginResponse>> logInUser(@RequestParam(value = "email") String email,
                                                              @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform) {
        EmailLoginResponse emailLoginResponse = loginService.loginUser(email, appPlatform);
        return ResponseEntity.ok(GenericResponse.<EmailLoginResponse>builder()
                .data(emailLoginResponse)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PostMapping(value = "/logout/{sessionToken}")
    public ResponseEntity<GenericResponse<String>> logoutUser(@PathVariable(value = SESSION_TOKEN) String sessionToken,
                                                              @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform) {
        String logoutMessage = loginService.logoutUser(sessionToken, appPlatform);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data(logoutMessage)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @DeleteMapping(value = "/login-edit/{phoneNumber}")
    public ResponseEntity<GenericResponse<String>> editPhoneNumber(@PathVariable(value = PHONE_NUMBER) String phoneNumber,
                                                                   @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform) {
        loginService.editLoginNumber(phoneNumber, appPlatform);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data("Deleted!")
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PutMapping(value = "/otp-verify/{phoneNumber}")
    public ResponseEntity<GenericResponse<LoginResponse>> userOtpVerify(@RequestParam(value = "otp") String otp,
                                                                 @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform,
                                                                 @PathVariable(value = PHONE_NUMBER) String phoneNumber) {
        LoginResponse loginResponse = loginService.otpVerification(otp, phoneNumber, appPlatform);
        return ResponseEntity.ok(GenericResponse.<LoginResponse>builder()
                .data(loginResponse)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PutMapping(value = "/resend-otp/{phoneNumber}")
    public ResponseEntity<GenericResponse<String>> resendOtp(@PathVariable(value = PHONE_NUMBER) String phoneNumber,
                                                             @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform) {
        String message = loginService.resendOtp(phoneNumber, appPlatform);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @GetMapping(value = "/check/{sessionToken}")
    public ResponseEntity<GenericResponse<?>> checkStatusOfSessionToken(@PathVariable(value = SESSION_TOKEN) String sessionToken,
                                                                        @RequestHeader(value = HEADER_PLATFORM) AppPlatform appPlatform) {
        Boolean isSessionExpired = loginService.checkExpiryOfSessionToken(sessionToken, appPlatform);

        return ResponseEntity.ok(GenericResponse.builder()
                .data(isSessionExpired)
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
