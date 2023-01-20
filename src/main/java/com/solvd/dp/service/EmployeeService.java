package com.solvd.dp.service;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;

import java.util.List;

public interface EmployeeService {

    Employee getById(Long id);

    Employee getByEmail(String email);

    List<Employee> getAllByRestaurantId(Long restaurantId);

    List<Employee> getAllByRestaurantIdAndPosition(Long restaurantId, EmployeePosition position);

    Employee update(Employee employee);

    Employee create(Employee employee, Long restaurantId);

    boolean exists(Long employeeId, Long restaurantId);

    boolean isManager(Long employeeId, Long restaurantId);

    void delete(Long id);

}
