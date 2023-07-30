package com.befoodly.be.dao;

import com.befoodly.be.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderDataDao {
    void save(OrderEntity orderEntity);
    List<OrderEntity> findOrderDetails(String customerReferenceId);
    Optional<OrderEntity> findAllPendingOrderDetails(String customerReferenceId);
    List<OrderEntity> findAllPlacedOrderDetails(String customerReferenceId);
}
