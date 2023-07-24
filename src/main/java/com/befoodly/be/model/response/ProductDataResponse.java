package com.befoodly.be.model.response;

import com.befoodly.be.model.Feedback;
import com.befoodly.be.model.ProductProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDataResponse {
    Long id;
    String referenceId;
    String title;
    String imgUrl;
    String description;
    Integer orderNo;
    Double price;
    LocalDateTime acceptingTime;
    LocalDateTime deliveryTime;
    Feedback feedback;
    Long vendorId;
    ProductProvider providerData;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
