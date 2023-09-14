package com.befoodly.be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductList {
    Long productId;
    String productName;
    Double cost;
    Integer orderCount;
}
