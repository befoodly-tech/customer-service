package com.befoodly.be.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailLoginResponse {
    String sessionToken;
    String phoneNumber;
}
