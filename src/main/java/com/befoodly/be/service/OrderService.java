package com.befoodly.be.service;

import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.model.response.OrderResponse;

import java.util.Optional;

public interface OrderService {
    void addOrderToCart(OrderRequest orderRequest);

    OrderResponse removeOrderInCart(OrderRequest orderRequest,
                         Optional<Integer> orderCount,
                         OrderStatus status);

    OrderResponse fetchAllPendingOrders(String customerReferenceId);
}
