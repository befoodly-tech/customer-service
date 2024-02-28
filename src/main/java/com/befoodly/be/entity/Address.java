package com.befoodly.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "address")
public class Address extends BaseEntity {

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String customerReferenceId;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String addressFirst;

    String addressSecond;

    @Column(nullable = false)
    String pinCode;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;
}
