package com.befoodly.be.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyOtpResponse {
    String customerReferenceId;
    String refreshToken;
    String authToken;
    String authType;
}
