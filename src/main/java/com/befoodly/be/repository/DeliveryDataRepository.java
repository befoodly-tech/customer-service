package com.befoodly.be.repository;

import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.model.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryDataRepository extends JpaRepository<DeliveryEntity, Long> {
    DeliveryEntity findByOrderId(Long orderId);

    @Query(nativeQuery = true, value = "select * from delivery_data where customer_reference_id=:customerReferenceId and status!='COMPLETED'")
    List<DeliveryEntity> findAllPendingDeliveryByCustomerId(String customerReferenceId);

    @Query(nativeQuery = true, value = "select * from delivery_data where status='PENDING' or status='INITIATED'")
    List<DeliveryEntity> findAllPendingDelivery();
}
