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
    String name;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    Long orderId;

    Double totalCost;

    @Enumerated(EnumType.STRING)
    DeliveryStatus status;
}
