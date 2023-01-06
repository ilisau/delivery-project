package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Order;
import com.example.dp.repository.OrderRepository;
import com.example.dp.service.OrderService;
import com.example.dp.web.dto.user.CreateOrderDto;
import com.example.dp.web.dto.user.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order getById(Long id) throws ResourceNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
    }

    @Override
    public List<Order> getAllByUserId(Long id) throws ResourceNotFoundException {
        return orderRepository.getAllByUserId(id);
    }

    @Override
    public List<Order> getAllByAddressId(Long id) throws ResourceNotFoundException {
        return orderRepository.getAllByAddressId(id);
    }

    @Override
    public List<Order> getAllByRestaurantId(Long id) throws ResourceNotFoundException {
        return orderRepository.getAllByRestaurantId(id);
    }

    @Override
    public List<Order> getAllByCourierId(Long id) throws ResourceNotFoundException {
        return orderRepository.getAllByCourierId(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order create(CreateOrderDto createOrderDto) {
        return orderRepository.create(createOrderDto);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.delete(id);
    }
}
