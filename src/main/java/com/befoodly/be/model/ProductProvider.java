package com.befoodly.be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductProvider {

    String providerName;

    String location;
}
