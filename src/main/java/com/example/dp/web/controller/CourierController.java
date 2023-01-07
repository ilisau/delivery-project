package com.example.dp.web.controller;

import com.example.dp.domain.courier.Courier;
import com.example.dp.service.CourierService;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.mapper.CourierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/couriers")
@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @GetMapping("/{id}")
    public CourierDto getById(@PathVariable Long id) {
        Courier courier = courierService.getById(id);
        return CourierMapper.INSTANCE.toDto(courier);
    }
}
