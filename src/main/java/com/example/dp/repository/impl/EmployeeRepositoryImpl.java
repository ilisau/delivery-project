package com.example.dp.repository.impl;

import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> getAllByRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public List<Employee> getAllByPosition(EmployeePosition position) {
        return null;
    }

    @Override
    public Employee save(Employee employeeDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
