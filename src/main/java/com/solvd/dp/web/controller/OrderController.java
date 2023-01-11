package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.user.OrderDto;
import com.solvd.dp.web.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody OrderDto dto) {
        Order order = OrderMapper.INSTANCE.toEntity(dto);
        orderService.save(order);
    }

    @GetMapping("/users/{userId}")
    public List<OrderDto> getAllByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getAllByUserId(userId);
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}")
    @Validated(OnCreate.class)
    public OrderDto create(@PathVariable Long userId, @Valid @RequestBody OrderDto dto) {
        Order orderToBeCreated = OrderMapper.INSTANCE.toEntity(dto);
        Order order = orderService.create(orderToBeCreated, userId);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @GetMapping("/addresses/{id}")
    public List<OrderDto> getAllByAddressId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByAddressId(id);
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/couriers/{id}")
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

    @PutMapping("/{id}/status/{status}")
    public void updateStatus(@PathVariable Long id, @PathVariable OrderStatus status) {
        orderService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

}
