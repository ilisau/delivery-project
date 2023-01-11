package com.solvd.dp.service;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;

import java.util.List;

public interface EmployeeService {

    Employee getById(Long id);

    List<Employee> getAllByRestaurantId(Long restaurantId);

    List<Employee> getAllByRestaurantIdAndPosition(Long restaurantId, EmployeePosition position);

    Employee save(Employee employee);

    Employee create(Employee employee, Long restaurantId);

    void delete(Long id);

}
