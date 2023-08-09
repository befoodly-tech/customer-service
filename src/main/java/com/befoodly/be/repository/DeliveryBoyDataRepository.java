package com.befoodly.be.repository;

import com.befoodly.be.entity.DeliveryBoyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryBoyDataRepository extends JpaRepository<DeliveryBoyEntity, Long> {
}
