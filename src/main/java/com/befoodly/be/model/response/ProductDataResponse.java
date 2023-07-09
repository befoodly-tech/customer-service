package com.befoodly.be.model.response;

import com.befoodly.be.model.ProductFeedback;
import com.befoodly.be.model.ProductProvider;
import com.befoodly.be.utils.LocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    ProductFeedback feedback;
    ProductProvider providerData;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
