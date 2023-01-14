package com.solvd.dp.service;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;

import java.util.List;

public interface CourierService {

    Courier getById(Long id);

    Courier getByOrderId(Long orderId);

    List<Courier> getAll();

    List<Courier> getAllByStatus(CourierStatus status);

    Courier update(Courier courier);

    Courier create(Courier courier);

    void assignOrder(Long courierId, Long orderId);

    void delete(Long id);

}
