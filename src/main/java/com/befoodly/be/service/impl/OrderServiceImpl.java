package com.befoodly.be.service.impl;

import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.dao.OrderDataDao;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.ProductList;
import com.befoodly.be.model.enums.DeliveryStatus;
import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.DeliveryOrderRequest;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.model.request.ProductEditRequest;
import com.befoodly.be.model.response.OrderResponse;
import com.befoodly.be.service.DeliveryBoyService;
import com.befoodly.be.service.OrderService;
import com.befoodly.be.service.ProductService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderDataDao orderDataDao;

    private final DeliveryDataDao deliveryDataDao;

    private final DeliveryBoyService deliveryBoyService;

    private final ProductService productService;

    @Override
    public void addOrderToCart(String customerReferenceId, OrderRequest orderRequest) {

        try {
            Optional<OrderEntity> optionalOrderEntity = orderDataDao.findAllPendingOrderDetails(customerReferenceId);

            if (optionalOrderEntity.isEmpty()) {
                String getReferenceId = UUID.randomUUID().toString();
                List<ProductList> productList = List.of(ProductList.builder()
                        .productId(orderRequest.getProductId())
                        .productName(orderRequest.getProductName())
                        .cost(orderRequest.getCost())
                        .orderCount(1).build());

                OrderEntity orderEntity = OrderEntity.builder()
                        .referenceId(getReferenceId)
                        .customerReferenceId(customerReferenceId)
                        .productList(JacksonUtils.objectToString(productList))
                        .status(OrderStatus.PENDING)
                        .totalCost(orderRequest.getCost())
                        .build();

                orderDataDao.save(orderEntity);
            } else {
                OrderEntity currentOrderEntity = optionalOrderEntity.get();
                List<ProductList> currentProductList = JacksonUtils.stringToListObject(currentOrderEntity.getProductList(), ProductList.class );

                Optional<ProductList> existingProductId = currentProductList.stream()
                        .filter(productList -> productList.getProductId().equals(orderRequest.getProductId()))
                        .findFirst();

                if (existingProductId.isEmpty()) {
                    ProductList newProductItem = ProductList.builder()
                            .productId(orderRequest.getProductId())
                            .productName(orderRequest.getProductName())
                            .cost(orderRequest.getCost())
                            .orderCount(1).build();

                    currentProductList.add(newProductItem);
                } else {
                    ProductList productList = existingProductId.get();
                    Integer currentCount = productList.getOrderCount();

                    int idx = currentProductList.indexOf(productList);

                    productList.setOrderCount(currentCount+1);
                    currentProductList.set(idx, productList);
                }

                Double newTotalCost = currentOrderEntity.getTotalCost() + orderRequest.getCost();

                currentOrderEntity.setTotalCost(Math.floor(newTotalCost));
                currentOrderEntity.setProductList(JacksonUtils.objectToString(currentProductList));

                orderDataDao.save(currentOrderEntity);
            }

            log.info("Successfully! saved the order in cart for customer id: {}", customerReferenceId);

        } catch (Exception e) {
            log.error("Failed to add the order in cart for customer id: {} due to error: {}",
                    customerReferenceId, e.getMessage());

            throw e;
        }
    }

    @Override
    public OrderResponse editOrderInCart(String customerReferenceId, OrderRequest orderRequest, Optional<Integer> orderCount) {

        try {
            Optional<OrderEntity> orderEntity = orderDataDao.findAllPendingOrderDetails(customerReferenceId);

            if (orderEntity.isEmpty()) {
                log.info("No order found with orderRequest: {}", orderRequest);
                throw new InvalidException("Invalid order request data!");
            }

            OrderEntity currentOrderEntity = orderEntity.get();
            List<ProductList> currentProductList = JacksonUtils.stringToListObject(currentOrderEntity.getProductList(), ProductList.class );

            Optional<ProductList> existingProductId = currentProductList.stream()
                    .filter(productList -> productList.getProductId().equals(orderRequest.getProductId()))
                    .findFirst();

            if (existingProductId.isEmpty()) {
                log.info("No product id: {} present for customer: {}", orderRequest.getProductId(), customerReferenceId);
                throw new InvalidException("Invalid product id!");
            }

            ProductList productList = existingProductId.get();
            Integer currentCount = productList.getOrderCount();

            int idx = currentProductList.indexOf(productList);

            if (orderCount.isPresent() && orderCount.get() <= currentCount) {
                Integer reducedCount = orderCount.get();
                Integer updatedCount = currentCount - reducedCount;

                if (updatedCount == 0) {
                    currentProductList.remove(productList);
                } else {
                    productList.setOrderCount(updatedCount);
                    currentProductList.set(idx, productList);
                }

                Double newTotalCost = currentOrderEntity.getTotalCost() - Math.floor(reducedCount*(orderRequest.getCost()));

                currentOrderEntity.setTotalCost(Math.floor(newTotalCost));
                currentOrderEntity.setProductList(JacksonUtils.objectToString(currentProductList));
            }

            orderDataDao.save(currentOrderEntity);
            log.info("Successfully! updated the order data.");

            return mapToOrderResponse(currentOrderEntity);
        } catch (Exception e) {
            log.error("Failed to update the order data with error: {} for orderRequest: {}", e.getMessage(), orderRequest);
            throw e;
        }
    }



    @Override
    public OrderResponse fetchAllPendingOrders(String customerReferenceId) {

        try {
            Optional<OrderEntity> orderEntity = orderDataDao.findAllPendingOrderDetails(customerReferenceId);

            if (orderEntity.isEmpty()) {
                log.info("No pending order found for the customer id: {}", customerReferenceId);
                return null;
            }

            OrderEntity currentOrderEntity = orderEntity.get();

            OrderResponse orderResponse = mapToOrderResponse(currentOrderEntity);

            log.info("Successfully! fetched the order data for customer: {}", customerReferenceId);

            return orderResponse;
        } catch (Exception e) {
            log.error("Failed to fetch the order data for customer: {} with error: {}", customerReferenceId, e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderResponse updateOrderDetails(String customerReferenceId, OrderStatus status) {
        try {
            Optional<OrderEntity> findPendingOrder = orderDataDao.findAllPendingOrderDetails(customerReferenceId);

            if (findPendingOrder.isEmpty()) {
                log.info("No pending order present for customer id: {}", customerReferenceId);
                throw new InvalidException("No pending order present for customer!");
            }

            OrderEntity currentOrderEntity = findPendingOrder.get();

            if (ObjectUtils.isNotEmpty(status)) {
                currentOrderEntity.setStatus(status);
            }

            orderDataDao.save(currentOrderEntity);
            log.info("updated the status of current order entity in the database!");

            return mapToOrderResponse(currentOrderEntity);
        } catch (Exception e) {
            log.error("received error: {} while changing status of pending order for customer: {}", e.getMessage(), customerReferenceId);
            throw e;
        }
    }

    @Override
    public Long placeYourOrderForDelivery(String customerReferenceId, DeliveryOrderRequest request) {
        try {
            Optional<OrderEntity> findPendingOrder = orderDataDao.findAllPendingOrderDetails(customerReferenceId);

            if (findPendingOrder.isEmpty()) {
                log.info("No pending order present for customer id: {}", customerReferenceId);
                throw new InvalidException("No pending order present for customer!");
            }

            OrderEntity currentOrderEntity = findPendingOrder.get();
            currentOrderEntity.setStatus(OrderStatus.PLACED);

            List<ProductList> productList = JacksonUtils.stringToListObject(currentOrderEntity.getProductList(), ProductList.class);
            updateProductOrderCount(productList);

            String referenceId = UUID.randomUUID().toString();

            DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                    .referenceId(referenceId)
                    .customerReferenceId(customerReferenceId)
                    .orderId(currentOrderEntity.getId())
                    .addressId(request.getAddressId())
                    .couponId(request.getCouponId())
                    .finalCost(request.getFinalCost())
                    .deliveryCost(request.getDeliveryCost())
                    .deliveryTime(request.getDeliverySlot())
                    .discountAmount(request.getDiscountAmount())
                    .status(DeliveryStatus.PENDING)
                    .description(request.getDescription())
                    .build();

            deliveryDataDao.save(deliveryEntity);
            orderDataDao.save(currentOrderEntity);
            log.info("Order id: {} for customer: {} is placed successfully!", currentOrderEntity.getId(),
                    customerReferenceId);

            return deliveryEntity.getId();
        } catch (Exception e) {
            log.error("Received error: {} while placing order for customer: {}", e.getMessage(), customerReferenceId);
            throw e;
        }
    }

    private void updateProductOrderCount(List<ProductList> productList) {
        productList.forEach(productList1 -> productService.updateProductDetails(
                productList1.getProductId(),
                ProductEditRequest.builder()
                        .orderNo(productList1.getOrderCount())
                        .build()));
    }

    @Override
    public OrderResponse fetchOrderDetails(Long orderId) {
        try {
            Optional<OrderEntity> orderEntity = orderDataDao.findOrderDetailsById(orderId);

            if (orderEntity.isEmpty()) {
                log.info("No order found with order id: {}", orderId);
                throw new InvalidException("No order found with order id!");
            }

            OrderResponse orderData = mapToOrderResponse(orderEntity.get());
            log.info("Successfully, fetched the order details with the id: {}", orderId);

            return orderData;
        } catch (Exception e) {
            log.error("Received an error: {} for order id: {}", e.getMessage(), orderId);
            throw e;
        }
    }

    private OrderResponse mapToOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .referenceId(orderEntity.getReferenceId())
                .productList(JacksonUtils.stringToListObject(orderEntity.getProductList(), ProductList.class))
                .totalCost(orderEntity.getTotalCost())
                .createdAt(orderEntity.getCreatedAt())
                .updatedAt(orderEntity.getUpdatedAt())
                .build();
    }

}
