package com.befoodly.be.entity;

import com.befoodly.be.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_history")
public class OrderEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    String referenceId;

    @Column(nullable = false)
    String customerReferenceId;

    @Column(nullable = false)
    Long productId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    Integer orderCount;
}
