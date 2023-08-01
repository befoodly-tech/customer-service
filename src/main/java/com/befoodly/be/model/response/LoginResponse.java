package com.befoodly.be.model.response;

import com.befoodly.be.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    Long id;
    String referenceId;
    CustomerEntity customerData;
    Boolean isCustomerExist;
}
