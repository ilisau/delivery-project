package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.repository.EmployeeRepository;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestaurantService restaurantService;

    @Override
    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllByRestaurantId(Long restaurantId) {
        return employeeRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllByRestaurantIdAndPosition(Long restaurantId, EmployeePosition position) {
        return employeeRepository.getAllByRestaurantIdAndPosition(restaurantId, position);
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee create(Employee employee, Long restaurantId) throws ResourceNotFoundException {
        if (employeeRepository.exists(employee, restaurantId)) {
            throw new ResourceAlreadyExistsException("Employee already exists :: " + employee);
        }
        employee = employeeRepository.create(employee);
        restaurantService.addEmployeeById(employee.getId(), restaurantId);
        return employee;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        employeeRepository.delete(id);
    }

}
