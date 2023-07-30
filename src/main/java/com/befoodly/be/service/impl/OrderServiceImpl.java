package com.befoodly.be.service.impl;

import com.befoodly.be.dao.DeliveryDataDao;
import com.befoodly.be.dao.OrderDataDao;
import com.befoodly.be.entity.DeliveryEntity;
import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.DeliveryManList;
import com.befoodly.be.model.ProductList;
import com.befoodly.be.model.enums.DeliveryStatus;
import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.model.response.OrderResponse;
import com.befoodly.be.service.OrderService;
import com.befoodly.be.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderDataDao orderDataDao;

    private final DeliveryDataDao deliveryDataDao;

    @Value("#{${delivery.man.data.list}}")
    Map<String, String> deliveryManData;

    @Override
    public void addOrderToCart(OrderRequest orderRequest) {

        try {
            Optional<OrderEntity> optionalOrderEntity = orderDataDao.findAllPendingOrderDetails(orderRequest.getCustomerReferenceId());

            if (optionalOrderEntity.isEmpty()) {
                String getReferenceId = UUID.randomUUID().toString();
                List<ProductList> productList = List.of(ProductList.builder()
                        .productId(orderRequest.getProductId())
                        .orderCount(1).build());

                OrderEntity orderEntity = OrderEntity.builder()
                        .referenceId(getReferenceId)
                        .customerReferenceId(orderRequest.getCustomerReferenceId())
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

            log.info("Successfully! saved the order in cart for customer id: {}", orderRequest.getCustomerReferenceId());

        } catch (Exception e) {
            log.error("Failed to add the order in cart for customer id: {} due to error: {}",
                    orderRequest.getCustomerReferenceId(), e.getMessage());

            throw e;
        }
    }

    @Override
    public OrderResponse removeOrderInCart(OrderRequest orderRequest, Optional<Integer> orderCount, OrderStatus status) {

        try {
            Optional<OrderEntity> orderEntity = orderDataDao.findAllPendingOrderDetails(orderRequest.getCustomerReferenceId());

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
                log.info("No product id: {} present for customer: {}", orderRequest.getProductId(), orderRequest.getCustomerReferenceId());
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

                if (currentProductList.isEmpty()) {
                    currentOrderEntity.setStatus(OrderStatus.REMOVED);
                }
            }

            if (ObjectUtils.isNotEmpty(status)) {
                currentOrderEntity.setStatus(status);

                if (OrderStatus.PLACED.equals(status)) {
                    String referenceId = UUID.randomUUID().toString();
                    DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                            .referenceId(referenceId)
                            .name(deliveryManData.get("name"))
                            .phoneNumber(deliveryManData.get("phoneNumber"))
                            .orderId(currentOrderEntity.getId())
                            .totalCost(currentOrderEntity.getTotalCost())
                            .status(DeliveryStatus.INITIATED)
                            .build();

                    deliveryDataDao.save(deliveryEntity);
                    log.info("Order id: {} for customer: {} is placed successfully!", currentOrderEntity.getId(),
                            orderRequest.getCustomerReferenceId());
                }
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
                throw new InvalidException("No pending order found for the customer!");
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

    private OrderResponse mapToOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .referenceId(orderEntity.getReferenceId())
                .customerReferenceId(orderEntity.getCustomerReferenceId())
                .productList(JacksonUtils.stringToListObject(orderEntity.getProductList(), ProductList.class))
                .status(orderEntity.getStatus())
                .totalCost(orderEntity.getTotalCost())
                .createdAt(orderEntity.getCreatedAt())
                .updatedAt(orderEntity.getUpdatedAt())
                .build();
    }

}
