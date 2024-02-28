package com.befoodly.be.entity;

import com.befoodly.be.model.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")
public class ProductEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String title;

    String imgUrl;

    String description;

    @Column(nullable = false)
    Integer orderNo;

    @Column(nullable = false)
    Double price;

    @Column(nullable = false)
    LocalDateTime acceptingTime;

    @Column(nullable = false)
    LocalDateTime deliveryTime;

    @Enumerated(EnumType.STRING)
    ProductStatus status;

    String feedback;

    Long vendorId;

    String providerData;
}
