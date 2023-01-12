package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.web.dto.user.OrderDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PutMapping
    public void save(@Validated(OnUpdate.class) @RequestBody OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        orderService.save(order);
    }

    @GetMapping("/users/{userId}")
    public List<OrderDto> getAllByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getAllByUserId(userId);
        return orderMapper.toDto(orders);
    }

    @PostMapping("/users/{userId}")
    public OrderDto create(@PathVariable Long userId,
                           @Validated(OnCreate.class) @RequestBody OrderDto dto) {
        Order orderToBeCreated = orderMapper.toEntity(dto);
        Order order = orderService.create(orderToBeCreated, userId);
        return orderMapper.toDto(order);
    }

    @GetMapping("/addresses/{id}")
    public List<OrderDto> getAllByAddressId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByAddressId(id);
        return orderMapper.toDto(orders);
    }

    @GetMapping("/couriers/{id}")
    public List<OrderDto> getAllByCourierId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByCourierId(id);
        return orderMapper.toDto(orders);
    }

    @GetMapping("/restaurants/{id}")
    public List<OrderDto> getAllByRestaurantId(@PathVariable Long id,
                                               @RequestParam(required = false) OrderStatus status) {
        List<Order> orders;
        if (status == null) {
            orders = orderService.getAllByRestaurantId(id);
        } else {
            orders = orderService.getAllByRestaurantIdAndStatus(id, status);
        }
        return orderMapper.toDto(orders);
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return orderMapper.toDto(order);
    }

    @PutMapping("/{id}/status/{status}")
    public void updateStatus(@PathVariable Long id,
                             @PathVariable OrderStatus status) {
        orderService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

}
