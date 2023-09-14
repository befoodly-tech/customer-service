package com.befoodly.be.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryOrderRequest {
    Double finalCost;
    Double deliveryCost;
    Double discountAmount;
    Long addressId;
    Long couponId;
    String description;
}
