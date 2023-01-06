package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.repository.EmployeeRepository;
import com.example.dp.service.EmployeeService;
import com.example.dp.web.dto.restaurant.CreateEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getById(Long id) throws ResourceNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
    }

    @Override
    public List<Employee> getAllByRestaurantId(Long id) {
        return employeeRepository.getAllByRestaurantId(id);
    }

    @Override
    public List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position) {
        return employeeRepository.getAllByRestaurantIdAndPosition(id, position);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee create(CreateEmployeeDto createEmployeeDto) throws ResourceNotFoundException {
        //TODO set restaurant
        Employee employee = employeeRepository.create(createEmployeeDto);
        return employee;
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.delete(id);
    }
}
