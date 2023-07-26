package com.befoodly.be.dao;

import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.model.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDataDao {
    void save(OrderEntity orderEntity);
    Optional<OrderEntity> findOrderDetails(String customerReferenceId, Long productId);
    List<OrderEntity> findAllPendingOrderDetails(String customerReferenceId);
}
