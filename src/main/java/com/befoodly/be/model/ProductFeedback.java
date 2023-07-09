package com.befoodly.be.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
public class ProductFeedback {

    Double rating;

    BigInteger reviews;
}
