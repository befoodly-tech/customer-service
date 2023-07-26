package com.befoodly.be.service;

import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.OrderRequest;

import java.util.List;

public interface OrderService {
    void addOrderToCart(OrderRequest orderRequest);

    void editOrderInCart(OrderRequest orderRequest,
                         Integer orderCount,
                         OrderStatus status);

    List<OrderEntity> fetchAllPendingOrders(String customerReferenceId);
}
