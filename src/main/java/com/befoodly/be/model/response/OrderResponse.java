package com.befoodly.be.model.response;

import com.befoodly.be.model.ProductList;
import com.befoodly.be.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {
    Long id;
    String referenceId;
    String customerReferenceId;
    List<ProductList> productList;
    OrderStatus status;
    Double totalCost;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
