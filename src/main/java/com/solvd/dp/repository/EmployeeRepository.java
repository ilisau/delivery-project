package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(Long id);

    List<Employee> getAllByRestaurantId(Long restaurantId);

    List<Employee> getAllByRestaurantIdAndPosition(Long restaurantId, EmployeePosition position);

    Employee save(Employee employee);

    Employee create(Employee employee);

    boolean exists(Employee employee, Long restaurantId);

    void delete(Long id);

}
