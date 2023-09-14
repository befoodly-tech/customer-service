package com.befoodly.be.service;

import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.DeliveryOrderRequest;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.model.response.OrderResponse;

import java.util.Optional;

public interface OrderService {
    void addOrderToCart(String customerReferenceId, OrderRequest orderRequest);

    OrderResponse editOrderInCart(String customerReferenceId, OrderRequest orderRequest,
                                  Optional<Integer> orderCount);

    OrderResponse fetchAllPendingOrders(String customerReferenceId);

    OrderResponse updateOrderDetails(String customerReferenceId, OrderStatus status);

    Long placeYourOrderForDelivery(String customerReferenceId, DeliveryOrderRequest request);

    OrderResponse fetchOrderDetails(Long orderId);
}
