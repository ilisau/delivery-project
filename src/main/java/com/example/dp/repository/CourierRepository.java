package com.example.dp.repository;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.web.dto.courier.CreateCourierDto;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    Optional<Courier> findById(Long id);

    Optional<Courier> findByOrderId(Long id);

    List<Courier> getAll();

    List<Courier> getAllByStatus(CourierStatus status);

    Courier save(Courier courier);

    Courier create(CreateCourierDto createCourierDto);

    void delete(Long id);
}
