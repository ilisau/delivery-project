package com.example.dp.web.controller;

import com.example.dp.domain.user.Order;
import com.example.dp.service.OrderService;
import com.example.dp.web.dto.user.CreateOrderDto;
import com.example.dp.web.dto.user.OrderDto;
import com.example.dp.web.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping
    public void save(@Valid @RequestBody OrderDto dto) {
        Order order = OrderMapper.INSTANCE.toEntity(dto);
        orderService.save(order);
    }

    @PostMapping
    public OrderDto create(@Valid @RequestBody CreateOrderDto dto) {
        Order order = orderService.create(dto);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @GetMapping("/by-user/{id}")
    public List<OrderDto> getAllByUserId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByUserId(id);
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-address/{id}")
    public List<OrderDto> getAllByAddressId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByAddressId(id);
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-courier/{id}")
    public List<OrderDto> getAllByCourierId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByCourierId(id);
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
