package com.solvd.dp.service.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.repository.CourierRepository;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final OrderService orderService;

    @Override
    @Transactional(readOnly = true)
    public Courier getById(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Courier getByOrderId(Long id) {
        return courierRepository.findByOrderId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found for this order :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Courier> getAll() {
        return courierRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Courier> getAllByStatus(CourierStatus status) {
        return courierRepository.getAllByStatus(status);
    }

    @Override
    @Transactional
    public Courier save(Courier courier) {
        //TODO check if courier exists
        return courierRepository.save(courier);
    }

    @Override
    @Transactional
    public Courier create(Courier courier) {
        if (courierRepository.isExists(courier)) {
            throw new ResourceAlreadyExistsException("Courier already exists :: " + courier);
        }
        return courierRepository.create(courier);
    }

    @Override
    @Transactional
    public void assignOrder(Long courierId, Long orderId) {
        if (!orderService.isOrderAssigned(orderId)) {
            orderService.assignOrder(orderId, courierId);
        } else {
            throw new IllegalStateException("Order already assigned");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courierRepository.delete(id);
    }

}
