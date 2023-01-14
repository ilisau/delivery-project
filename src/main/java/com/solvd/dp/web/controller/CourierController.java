package com.solvd.dp.web.controller;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.web.dto.courier.CourierDto;
import com.solvd.dp.web.dto.user.OrderDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.CourierMapper;
import com.solvd.dp.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/couriers")
@RestController
@RequiredArgsConstructor
@Validated
public class CourierController {

    private final CourierService courierService;
    private final OrderService orderService;

    private final CourierMapper courierMapper;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<CourierDto> getAll(@RequestParam(required = false) CourierStatus status) {
        List<Courier> couriers;
        if (status == null) {
            couriers = courierService.getAll();
        } else {
            couriers = courierService.getAllByStatus(status);
        }
        return courierMapper.toDto(couriers);
    }

    @PutMapping
    public void save(@Validated(OnUpdate.class) @RequestBody CourierDto courierDto) {
        Courier courier = courierMapper.toEntity(courierDto);
        courierService.update(courier);
    }

    @PostMapping
    public CourierDto create(@Validated(OnCreate.class) @RequestBody CourierDto courierDto) {
        Courier courierToBeCreated = courierMapper.toEntity(courierDto);
        Courier courier = courierService.create(courierToBeCreated);
        return courierMapper.toDto(courier);
    }

    @GetMapping("/{id}")
    public CourierDto getById(@PathVariable Long id) {
        Courier courier = courierService.getById(id);
        return courierMapper.toDto(courier);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        courierService.delete(id);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getAllOrdersByCourierId(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByCourierId(id);
        return orderMapper.toDto(orders);
    }

}
