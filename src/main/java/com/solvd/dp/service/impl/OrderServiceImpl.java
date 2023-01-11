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
    public List<Order> getAllByUserId(Long id) {
        return orderRepository.getAllByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllByAddressId(Long id) {
        return orderRepository.getAllByAddressId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllByCourierId(Long id) {
        return orderRepository.getAllByCourierId(id);
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
        cartService.setCartToUserById(userId);
        return orderRepository.findById(order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    @Transactional
    public void changeStatus(Long id, OrderStatus status) {
        orderRepository.changeStatus(id, status);
        if (status == OrderStatus.DELIVERED) {
            orderRepository.setDelivered(id);
        }
    }

    @Override
    @Transactional
    public boolean isOrderAssigned(Long orderId) {
        return orderRepository.isOrderAssigned(orderId);
    }

    @Override
    @Transactional
    public void assignOrder(Long orderId, Long courierId) {
        orderRepository.assignOrder(orderId, courierId);
    }

    @Override
    @Transactional
    public void updateStatus(Long orderId, OrderStatus status) {
        orderRepository.updateStatus(orderId, status);
    }

    @Override
    @Transactional
    public void addOrderById(Long userId, Long orderId) {
        orderRepository.addOrderById(userId, orderId);
    }

    @Override
    @Transactional
    public void deleteOrderById(Long userId, Long orderId) {
        orderRepository.deleteOrderById(userId, orderId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderRepository.delete(id);
    }

}
