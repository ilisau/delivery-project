package com.solvd.dp.service;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;

import java.util.List;

public interface EmployeeService {

    Employee getById(Long id);

    List<Employee> getAllByRestaurantId(Long id);

    List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position);

    Employee save(Employee employee);

    Employee create(Employee employee, Long restaurantId);

    void deleteById(Long id);

}
