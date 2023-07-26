package com.befoodly.be.repository;

import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByCustomerReferenceIdAndProductId(String customerReferenceId, Long productId);
    List<OrderEntity> findByCustomerReferenceIdAndStatus(String customerReferenceId, OrderStatus status);
}
