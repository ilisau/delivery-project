package com.example.dp.repository;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    Optional<Courier> findById(Long id);

    List<Courier> getAll();

    List<Courier> getAllByStatus(CourierStatus status);

    Courier save(Courier courier);

    void delete(Long id);
}
