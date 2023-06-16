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
@Table(name = "customer_data")
public class CustomerEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    String referenceId;

    String name;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String sessionToken;

    String email;

    String address;

    Boolean isActive;
}
