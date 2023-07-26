package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.OrderDataDao;
import com.befoodly.be.entity.OrderEntity;
import com.befoodly.be.model.enums.OrderStatus;
import com.befoodly.be.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderDataDaoImpl implements OrderDataDao {

    private final OrderRepository orderRepository;

    @Override
    public void save(OrderEntity orderEntity) {
        orderRepository.saveAndFlush(orderEntity);
    }

    @Override
    public Optional<OrderEntity> findOrderDetails(String customerReferenceId, Long productId) {
        return orderRepository.findByCustomerReferenceIdAndProductId(customerReferenceId, productId);
    }

    @Override
    public List<OrderEntity> findAllPendingOrderDetails(String customerReferenceId) {
        return orderRepository.findByCustomerReferenceIdAndStatus(customerReferenceId, OrderStatus.PENDING);
    }
}
