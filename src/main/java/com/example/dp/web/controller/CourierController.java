package com.example.dp.web.controller;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.service.CourierService;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.dto.courier.CreateCourierDto;
import com.example.dp.web.mapper.CourierMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/couriers")
@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @GetMapping
    public List<CourierDto> getAll() {
        List<Courier> couriers = courierService.getAll();
        return couriers.stream()
                .map(CourierMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-status/{status}")
    public List<CourierDto> getAllByStatus(@PathVariable CourierStatus status) {
        List<Courier> couriers = courierService.getAllByStatus(status);
        return couriers.stream()
                .map(CourierMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    public void save(@Valid @RequestBody CourierDto courierDto) {
        Courier courier = CourierMapper.INSTANCE.toEntity(courierDto);
        courierService.save(courier);
    }

    @PostMapping
    public CourierDto create(@Valid @RequestBody CreateCourierDto courierDto) {
        Courier courier = courierService.create(courierDto);
        return CourierMapper.INSTANCE.toDto(courier);
    }

    @GetMapping("/{id}")
    public CourierDto getById(@PathVariable Long id) {
        Courier courier = courierService.getById(id);
        return CourierMapper.INSTANCE.toDto(courier);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        courierService.deleteById(id);
    }
}
