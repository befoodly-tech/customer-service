package com.befoodly.be.controller;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.response.GenericResponse;
import com.befoodly.be.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.befoodly.be.model.constant.CommonConstants.REFERENCE_ID;

@RestController
@RequestMapping("/v1")
@Log4j2
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<String>> loginUser(@RequestParam(value = "phone_number") String phoneNumber,
                                                             @RequestHeader(value = "platform") AppPlatform appPlatform) {
        String referenceId = loginService.loginUser(phoneNumber, appPlatform);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data(referenceId)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<String>> logoutUser(@RequestParam(value = "phone_number") String phoneNumber,
                                                             @RequestHeader(value = "platform") AppPlatform appPlatform) {
        String logoutMessage = loginService.logoutUser(phoneNumber, appPlatform);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data(logoutMessage)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @DeleteMapping(value = "/login-edit/{referenceId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<String>> editPhoneNumber(@PathVariable(value = REFERENCE_ID) String referenceId) {
        loginService.editLoginNumber(referenceId);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data("Deleted")
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PutMapping(value = "/otp-verify/{referenceId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<String>> userOtpVerify(@RequestParam(value = "otp") String otp,
                                                             @PathVariable(value = REFERENCE_ID) String referenceId) {
        String message = loginService.otpVerification(otp, referenceId);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

    @PostMapping(value = "/resend-otp/{referenceId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<String>> resendOtp(@PathVariable(value = REFERENCE_ID) String referenceId) {
        String message = loginService.resendOtp(referenceId);
        return ResponseEntity.ok(GenericResponse.<String>builder()
                .data(message)
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}
