package com.befoodly.be.model.request;

import com.befoodly.be.model.Feedback;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryBoyCreateRequest {
    String name;
    String imgUrl;
    String phoneNumber;
    String panNumber;
    String description;
    AddressCreateRequest address;
    Feedback feedback;
}
