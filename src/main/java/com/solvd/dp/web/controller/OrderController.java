package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.web.dto.user.OrderDto;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Order", description = "Order API")
public class OrderController {

    private final OrderService orderService;
    private final CourierService courierService;

    private final OrderMapper orderMapper;

    @PutMapping
    @PreAuthorize("canAccessOrder(#dto.id)")
    @Operation(summary = "Update order")
    public void update(@Validated(OnUpdate.class) @RequestBody OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        orderService.update(order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("canAccessOrder(#id)")
    @Operation(summary = "Get order by id")
    public OrderDto getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return orderMapper.toDto(order);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("canAccessOrder(#id)")
    @Operation(summary = "Delete order by id")
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

    @PutMapping("/{id}/courier/{courierId}")
    @PreAuthorize("canAccessOrder(#id)")
    @Operation(summary = "Assign courier to order")
    public void assign(@PathVariable Long id,
                       @PathVariable Long courierId) {
        courierService.assignOrder(courierId, id);
    }

    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("canAccessOrder(#id)")
    @Operation(summary = "Update order status")
    public void updateStatus(@PathVariable Long id,
                             @PathVariable OrderStatus status) {
        orderService.updateStatus(id, status);
    }

}
