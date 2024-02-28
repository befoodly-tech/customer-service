package com.befoodly.be.model.response;

import com.befoodly.be.entity.Address;
import com.befoodly.be.model.DeliveryManData;
import com.befoodly.be.model.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryResponse {
    Long id;
    Long orderId;
    Double finalCost;
    Double discountCost;
    Double deliveryCost;
    DeliveryStatus status;
    DeliveryManData deliveryManData;
    Address deliveryAddress;
    LocalDateTime deliveryTime;
    LocalDateTime orderTime;
    String description;
}
