package com.befoodly.be.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressCreateRequest {
    String title;
    String phoneNumber;
    String addressFirst;
    String addressSecond;
    String pinCode;
    String city;
    String state;
}
