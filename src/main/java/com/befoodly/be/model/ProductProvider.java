package com.befoodly.be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
public class ProductProvider {

    BigInteger providerId;

    String providerName;

    String location;
}
