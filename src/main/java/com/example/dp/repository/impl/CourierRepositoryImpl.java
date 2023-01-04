package com.example.dp.repository.impl;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierRepositoryImpl implements CourierRepository {

    @Override
    public Optional<Courier> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Courier> getAll() {
        return null;
    }

    @Override
    public List<Courier> getAllByStatus(CourierStatus status) {
        return null;
    }

    @Override
    public Courier save(Courier courierDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
