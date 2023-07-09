package com.befoodly.be.model.request;

import com.befoodly.be.model.ProductFeedback;
import com.befoodly.be.model.ProductProvider;
import com.befoodly.be.utils.LocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ProductCreateRequest {
    String title;
    String imgUrl;
    String description;
    Integer orderNo;
    Double price;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime acceptingTime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime deliveryTime;
    ProductFeedback feedback;
    ProductProvider providerData;
}
