package com.befoodly.be.model.response;

import com.befoodly.be.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    Long id;
    String referenceId;
    Customer customerData;
    Boolean isCustomerDataExist;
}
