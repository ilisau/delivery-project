package com.example.dp.repository;

import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.CreateEmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(Long id);

    List<Employee> getAllByRestaurantId(Long id);

    List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position);

    Employee save(Employee employee);

    Employee create(CreateEmployeeDto dto);

    void delete(Long id);
}
