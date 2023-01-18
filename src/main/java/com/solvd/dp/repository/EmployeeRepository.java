package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeRepository {

    Optional<Employee> findById(Long id);

    List<Employee> getAllByRestaurantId(Long restaurantId);

    List<Employee> getAllByRestaurantIdAndPosition(@Param("restaurantId") Long restaurantId, @Param("position") EmployeePosition position);

    void update(Employee employee);

    void create(Employee employee);

    boolean exists(@Param("employee") Employee employee, @Param("restaurantId") Long restaurantId);

    void delete(Long id);

}
