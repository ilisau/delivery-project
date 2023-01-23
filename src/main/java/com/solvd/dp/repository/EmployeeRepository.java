package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.domain.user.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface EmployeeRepository {

    Optional<Employee> findById(Long id);

    Optional<Employee> findByEmail(String email);

    List<Employee> getAllByRestaurantId(Long restaurantId);

    List<Employee> getAllByRestaurantIdAndPosition(@Param("restaurantId") Long restaurantId, @Param("position") EmployeePosition position);

    void update(Employee employee);

    void create(Employee employee);

    void saveRoles(@Param("id") Long id, @Param("roles") Set<Role> roles);

    boolean exists(@Param("employee") Employee employee, @Param("restaurantId") Long restaurantId);

    boolean isManager(@Param("employeeId") Long employeeId, @Param("restaurantId") Long restaurantId);

    void delete(Long id);

}
