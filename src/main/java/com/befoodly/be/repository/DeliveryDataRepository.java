package com.befoodly.be.repository;

import com.befoodly.be.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDataRepository extends JpaRepository<DeliveryEntity, Long> {
    DeliveryEntity findByOrderId(Long orderId);
}
