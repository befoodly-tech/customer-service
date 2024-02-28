package com.befoodly.be.model.request;

import com.befoodly.be.model.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class VendorRequest {
    String name;
    String description;
    String imgUrl;
    String phoneNumber;
    String email;
    String address;
    List<Integer> cookList;
    Feedback feedback;
}
