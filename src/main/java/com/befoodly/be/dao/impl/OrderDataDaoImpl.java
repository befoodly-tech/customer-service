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
    public List<OrderEntity> findOrderDetails(String customerReferenceId) {
        return orderRepository.findByCustomerReferenceId(customerReferenceId);
    }

    @Override
    public Optional<OrderEntity> findAllPendingOrderDetails(String customerReferenceId) {
        List<OrderEntity> pendingOrderList = orderRepository.findByCustomerReferenceIdAndStatus(customerReferenceId, OrderStatus.PENDING);

        if (pendingOrderList.isEmpty()) {
            return Optional.ofNullable(null);
        }

        return Optional.of(pendingOrderList.get(0));
    }

    @Override
    public List<OrderEntity> findAllPlacedOrderDetails(String customerReferenceId) {
        return orderRepository.findByCustomerReferenceIdAndStatus(customerReferenceId, OrderStatus.PLACED);
    }

    @Override
    public Optional<OrderEntity> findOrderDetailsById(Long id) {
        return orderRepository.findById(id);
    }
}
