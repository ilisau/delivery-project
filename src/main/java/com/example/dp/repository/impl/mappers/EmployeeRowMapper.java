package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class EmployeeRowMapper implements RowMapper<Employee> {

    @SneakyThrows
    public static Employee mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setName(resultSet.getString("name"));
            employee.setPosition(EmployeePosition.valueOf(resultSet.getString("position")));
            return employee;
        } else {
            return null;
        }
    }

    @SneakyThrows
    public static List<Employee> mapRows(ResultSet resultSet) {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong("id"));
            employee.setName(resultSet.getString("name"));
            employee.setPosition(EmployeePosition.valueOf(resultSet.getString("position")));
            employees.add(employee);
        }
        return employees;
    }
}
