package com.befoodly.be.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderRequest {
    String customerReferenceId;
    Long productId;
}
