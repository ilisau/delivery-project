package com.example.dp.service;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.dto.courier.CreateCourierDto;

import java.util.List;

public interface CourierService {

    Courier getById(Long id) throws ResourceNotFoundException;

    List<Courier> getAll();

    List<Courier> getAllByStatus(CourierStatus status);

    Courier save(Courier courier);

    Courier create(CreateCourierDto createCourierDto);

    void deleteById(Long id);
}
