package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(Long id);

    List<Employee> getAllByRestaurantId(Long id);

    List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position);

    Employee save(Employee employee);

    Employee create(Employee employee);

    boolean isExists(Employee employee, Long restaurantId);

    void delete(Long id);

}
