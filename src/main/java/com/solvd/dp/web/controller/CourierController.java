package com.solvd.dp.web.controller;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.courier.CourierDto;
import com.solvd.dp.web.mapper.CourierMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/couriers")
@RestController
@RequiredArgsConstructor
@Validated
public class CourierController {

    private final CourierService courierService;

    @GetMapping
    public List<CourierDto> getAll(@RequestParam(required = false) CourierStatus status) {
        List<Courier> couriers;
        if (status == null) {
            couriers = courierService.getAll();
        } else {
            couriers = courierService.getAllByStatus(status);
        }
        return couriers.stream()
                .map(CourierMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody CourierDto courierDto) {
        Courier courier = CourierMapper.INSTANCE.toEntity(courierDto);
        courierService.save(courier);
    }

    @PostMapping
    @Validated(OnCreate.class)
    public CourierDto create(@Valid @RequestBody CourierDto courierDto) {
        Courier courierToBeCreated = CourierMapper.INSTANCE.toEntity(courierDto);
        Courier courier = courierService.create(courierToBeCreated);
        return CourierMapper.INSTANCE.toDto(courier);
    }

    @PutMapping("/{id}/orders/{orderId}")
    public void assignOrder(@PathVariable Long id,
                            @PathVariable Long orderId) {
        courierService.assignOrder(id, orderId);
    }

    @GetMapping("/{id}")
    public CourierDto getById(@PathVariable Long id) {
        Courier courier = courierService.getById(id);
        return CourierMapper.INSTANCE.toDto(courier);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        courierService.delete(id);
    }

}
