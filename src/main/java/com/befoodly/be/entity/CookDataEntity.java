package com.befoodly.be.entity;

import com.befoodly.be.model.enums.CookStatus;
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
@Table(name = "cook_data")
public class CookDataEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    String referenceId;

    String name;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String panNumber;

    Long vendorId;

    String description;

    String address;

    String specialities;

    String feedback;

    @Enumerated(EnumType.STRING)
    CookStatus status;
}
