package com.example.dp.service;

import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.web.dto.restaurant.CreateEmployeeDto;

import java.util.List;

public interface EmployeeService {

    Employee getById(Long id) throws ResourceNotFoundException;

    List<Employee> getAllByRestaurantId(Long id);

    List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position);

    Employee save(Employee employee);

    Employee create(CreateEmployeeDto createEmployeeDto) throws ResourceNotFoundException;

    void deleteById(Long id);
}
