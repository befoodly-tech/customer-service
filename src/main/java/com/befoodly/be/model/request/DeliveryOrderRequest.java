package com.befoodly.be.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
    LocalDateTime deliverySlot;
    String description;
}
