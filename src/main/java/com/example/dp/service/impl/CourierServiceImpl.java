package com.example.dp.service.impl;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.repository.CourierRepository;
import com.example.dp.service.CourierService;
import com.example.dp.web.dto.courier.CreateCourierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    public Courier getById(Long id) throws ResourceNotFoundException {
        return courierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Courier not found for this id :: " + id));
    }

    @Override
    public List<Courier> getAll() {
        return courierRepository.getAll();
    }

    @Override
    public List<Courier> getAllByStatus(CourierStatus status) {
        return courierRepository.getAllByStatus(status);
    }

    @Override
    public Courier save(Courier courier) {
        return courierRepository.save(courier);
    }

    @Override
    public Courier create(CreateCourierDto createCourierDto) {
        return courierRepository.create(createCourierDto);
    }

    @Override
    public void deleteById(Long id) {
        courierRepository.delete(id);
    }
}
