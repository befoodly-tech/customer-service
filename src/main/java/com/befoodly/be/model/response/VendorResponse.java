package com.befoodly.be.model.response;

import com.befoodly.be.model.Feedback;
import com.befoodly.be.model.enums.VendorStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorResponse {
    Long id;
    String referenceId;
    String name;
    String description;
    String imgUrl;
    String phoneNumber;
    VendorStatus status;
    String email;
    String address;
    List<CookProfileResponse> cookList;
    List<ProductDataResponse> productList;
    Feedback feedback;
}
