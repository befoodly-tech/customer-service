package com.befoodly.be.repository;

import com.befoodly.be.entity.DeliveryBoyEntity;
import com.befoodly.be.model.enums.DeliveryBoyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryBoyDataRepository extends JpaRepository<DeliveryBoyEntity, Long> {
    List<DeliveryBoyEntity> findByStatus(DeliveryBoyStatus status);
}
