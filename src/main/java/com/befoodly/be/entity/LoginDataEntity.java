package com.befoodly.be.entity;

import com.befoodly.be.model.enums.AppPlatform;
import com.befoodly.be.model.enums.ExpiryReason;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "login_data")
public class LoginDataEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String phoneNumber;

    String sessionToken;

    @Column(nullable = false)
    Boolean isExpired;

    @Enumerated(EnumType.STRING)
    ExpiryReason expiryReason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AppPlatform appPlatform;

    String otp;

    Boolean otpVerified;
}
