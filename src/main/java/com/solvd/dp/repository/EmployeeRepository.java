package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeRepository {

    Optional<Employee> findById(@Param("id") Long id);

    List<Employee> getAllByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<Employee> getAllByRestaurantIdAndPosition(@Param("restaurantId") Long restaurantId, @Param("position") EmployeePosition position);

    void update(@Param("employee") Employee employee);

    void create(@Param("employee") Employee employee);

    boolean exists(@Param("employee") Employee employee, @Param("restaurantId") Long restaurantId);

    void delete(@Param("id") Long id);

}
