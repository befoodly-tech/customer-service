package com.befoodly.be.entity;

import com.befoodly.be.model.enums.VendorStatus;
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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vendor")
public class VendorEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String name;

    String imgUrl;

    String description;

    @Column(nullable = false, unique = true)
    String phoneNumber;

    String email;

    @Column(nullable = false)
    String address;

    @Enumerated(EnumType.STRING)
    VendorStatus status;

    String cookList;

    String feedback;
}
