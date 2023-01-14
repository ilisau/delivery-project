package com.solvd.dp.repository;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    Optional<Courier> findById(Long id);

    Optional<Courier> findByOrderId(Long orderId);

    List<Courier> getAll();

    List<Courier> getAllByStatus(CourierStatus status);

    void update(Courier courier);

    void create(Courier courier);

    boolean exists(Courier courier);

    void delete(Long id);

}
