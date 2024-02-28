package com.befoodly.be.entity;

import com.befoodly.be.model.enums.AppPlatform;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "auth_log")
public class AuthLog extends BaseEntity {

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String phoneNumber;

    String otp;

    Boolean isValidated;

    int validationAttempts;

    LocalDateTime expirationTime;

    AppPlatform platform;
}