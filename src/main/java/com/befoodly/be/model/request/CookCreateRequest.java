package com.befoodly.be.model.request;

import com.befoodly.be.model.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CookCreateRequest {
    String name;
    String phoneNumber;
    String panNumber;
    Long vendorId;
    String description;
    AddressCreateRequest address;
    List<String> specialities;
    Feedback feedback;
}