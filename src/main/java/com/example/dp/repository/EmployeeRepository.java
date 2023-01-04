package com.example.dp.repository;

import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.domain.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(Long id);

    List<Employee> getAllByRestaurant(Restaurant restaurant);

    List<Employee> getAllByPosition(EmployeePosition position);

    Employee save(Employee employee);

    void delete(Long id);
}
