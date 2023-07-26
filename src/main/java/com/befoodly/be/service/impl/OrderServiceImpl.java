package com.befoodly.be.service.impl;

import com.befoodly.be.dao.OrderDataDao;
import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.exception.throwable.InvalidException;
import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.model.request.OrderRequest;
import com.befoodly.be.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderDataDao orderDataDao;

    @Override
    public void addOrderToCart(OrderRequest orderRequest) {

        try {
            String getReferenceId = UUID.randomUUID().toString();

            OrderEntity orderEntity = OrderEntity.builder()
                    .referenceId(getReferenceId)
                    .customerReferenceId(orderRequest.getCustomerReferenceId())
                    .productId(orderRequest.getProductId())
                    .status(OrderStatus.PENDING)
                    .orderCount(1)
                    .build();

            orderDataDao.save(orderEntity);
            log.info("Successfully! saved the order in cart for customer id: {}", orderRequest.getCustomerReferenceId());

        } catch (Exception e) {
            log.error("Failed to add the order in cart for customer id: {} due to error: {}",
                    orderRequest.getCustomerReferenceId(), e.getMessage());

            throw e;
        }
    }

    @Override
    public void editOrderInCart(OrderRequest orderRequest, Integer orderCount, OrderStatus status) {

        try {
            Optional<OrderEntity> orderEntity = orderDataDao.findOrderDetails(orderRequest.getCustomerReferenceId(), orderRequest.getProductId());

            if (orderEntity.isEmpty()) {
                log.info("No order found with orderRequest: {}", orderRequest);
                throw new InvalidException("Invalid order request data!");
            }

            OrderEntity updatedOrderEntity = orderEntity.get();

            if (ObjectUtils.isNotEmpty(orderCount) && orderCount >= 0) {
                updatedOrderEntity.setOrderCount(orderCount);

                if (orderCount == 0) {
                    updatedOrderEntity.setStatus(OrderStatus.REMOVED);
                }
            }

            if (ObjectUtils.isNotEmpty(status)) {
                updatedOrderEntity.setStatus(status);
            }

            orderDataDao.save(updatedOrderEntity);
            log.info("Successfully! updated the order data.");
        } catch (Exception e) {
            log.error("Failed to update the order data with error: {} for orderRequest: {}", e.getMessage(), orderRequest);
            throw e;
        }
    }

    @Override
    public List<OrderEntity> fetchAllPendingOrders(String customerReferenceId) {

        try {
            List<OrderEntity> orderEntityList = orderDataDao.findAllPendingOrderDetails(customerReferenceId);
            log.info("Successfully! fetched the order data for customer: {}", customerReferenceId);

            return orderEntityList;
        } catch (Exception e) {
            log.error("Failed to fetch the order data for customer: {} with error: {}", customerReferenceId, e.getMessage());

            throw e;
        }
    }
}
