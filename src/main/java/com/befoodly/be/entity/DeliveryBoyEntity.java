package com.befoodly.be.entity;

import com.befoodly.be.model.enums.DeliveryBoyStatus;
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
@Table(name = "delivery_boy")
public class DeliveryBoyEntity extends BaseEntity {

    @Column(nullable = false)
    String referenceId;

    @Column(nullable = false)
    String name;

    String imgUrl;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String panNumber;

    String description;

    String address;

    String feedback;

    @Enumerated(EnumType.STRING)
    DeliveryBoyStatus status;
}
