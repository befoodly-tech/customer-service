package com.befoodly.be.entity;

import com.befoodly.be.model.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "delivery_data")
public class DeliveryEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String customerReferenceId;

    Long deliveryBoyId;

    @Column(nullable = false)
    Long orderId;

    @Column(nullable = false)
    Long addressId;

    Long couponId;

    Double finalCost;

    Double deliveryCost;

    Double discountAmount;

    @Enumerated(EnumType.STRING)
    DeliveryStatus status;

    LocalDateTime deliveryTime;

    String description;
}
