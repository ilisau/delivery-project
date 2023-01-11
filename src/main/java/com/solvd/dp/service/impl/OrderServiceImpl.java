package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.repository.OrderRepository;
import com.solvd.dp.service.CartService;
import com.solvd.dp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Override
    @Transactional(readOnly = true)
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.getAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllByAddressId(Long addressId) {
        return orderRepository.getAllByAddressId(addressId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllByCourierId(Long courierId) {
        return orderRepository.getAllByCourierId(courierId);
    }

    @Override
    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order create(Order order, Long userId) {
        order = orderRepository.create(order);
        orderRepository.addOrderById(userId, order.getId());
        cartService.setEmptyByUserId(userId);
        return orderRepository.findById(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public boolean isOrderAssigned(Long id) {
        return orderRepository.isOrderAssigned(id);
    }

    @Override
    @Transactional
    public void assignOrder(Long orderId, Long courierId) {
        orderRepository.assignOrder(orderId, courierId);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, OrderStatus status) {
        orderRepository.updateStatus(id, status);
        if (status == OrderStatus.DELIVERED) {
            orderRepository.setDelivered(id);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderRepository.delete(id);
    }

}
