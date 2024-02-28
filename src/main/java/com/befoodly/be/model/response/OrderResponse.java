package com.befoodly.be.model.response;

import com.befoodly.be.model.ProductList;
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
    List<ProductList> productList;
    Double totalCost;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
