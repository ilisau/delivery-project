package com.solvd.dp.repository.mappers;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.domain.user.Role;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class EmployeeRowMapper implements RowMapper<Employee> {

    @SneakyThrows
    public static Employee mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("employee_id"));
            if (!resultSet.wasNull()) {
                employee.setName(resultSet.getString("employee_name"));
                employee.setPosition(EmployeePosition.valueOf(resultSet.getString("employee_position")));
                employee.setRoles(Set.of(Role.valueOf(resultSet.getString("employee_role"))));
                return employee;
            }
        }
        return null;
    }

    @SneakyThrows
    public static List<Employee> mapRows(ResultSet resultSet) {
        Set<Employee> employees = new HashSet<>();
        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("employee_id"));
            if (!resultSet.wasNull()) {
                employee.setName(resultSet.getString("employee_name"));
                employee.setPosition(EmployeePosition.valueOf(resultSet.getString("employee_position")));
                employee.setRoles(Set.of(Role.valueOf(resultSet.getString("employee_role"))));
                employees.add(employee);
            }
        }
        return employees.stream().toList();
    }

}
