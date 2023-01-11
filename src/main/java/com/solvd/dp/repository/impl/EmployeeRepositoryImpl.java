package com.solvd.dp.repository.impl;

import com.solvd.dp.config.DataSourceConfig;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.repository.EmployeeRepository;
import com.solvd.dp.repository.impl.mappers.EmployeeRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String FIND_ALL_BY_RESTAURANT_ID = """
            SELECT employees.id       as employee_id,
                   employees.name     as employee_name,
                   employees.position as employee_position
            FROM restaurants_employees
                     LEFT JOIN employees ON employee_id = employees.id
            WHERE restaurants_employees.restaurant_id = ?""";
    private static final String FIND_BY_ID = """
            SELECT id       as employee_id,
                   name     as employee_name,
                   position as employee_position
            FROM employees
            WHERE id = ?""";
    private static final String FIND_ALL_BY_RESTAURANT_ID_AND_POSITION = """
            SELECT e.id       as employee_id,
                   e.name     as employee_name,
                   e.position as employee_position
            FROM restaurants_employees
                     LEFT JOIN employees e ON employee_id = e.id
            WHERE restaurants_employees.restaurant_id = ?
            AND e.position = ?""";
    private static final String IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM restaurants_employees WHERE employee_id = ? AND restaurant_id = ?)";
    private final DataSourceConfig dataSourceConfig;
    private static final String SAVE_BY_ID = "UPDATE employees SET name = ?, position = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO employees (name, position) VALUES (?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM employees WHERE id = ?";

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet employeeResultSet = statement.executeQuery();
            return Optional.ofNullable(EmployeeRowMapper.mapRow(employeeResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employee by id :: " + id);
        }
    }

    @Override
    public List<Employee> getAllByRestaurantId(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID);
            statement.setLong(1, id);
            ResultSet employeeResultSet = statement.executeQuery();
            return EmployeeRowMapper.mapRows(employeeResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employees by restaurant id :: " + id);
        }
    }

    @Override
    public List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID_AND_POSITION);
            statement.setLong(1, id);
            statement.setString(2, position.name());
            ResultSet employeeResultSet = statement.executeQuery();
            return EmployeeRowMapper.mapRows(employeeResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employees by restaurant id and position :: " + id + " :: " + position);
        }
    }

    @Override
    public Employee save(Employee employee) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition().name());
            statement.setLong(3, employee.getId());
            statement.executeUpdate();
            return employee;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving employee :: " + employee);
        }
    }

    @Override
    public Employee create(Employee employee) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition().name());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            employee.setId(key.getLong(1));
            return employee;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating employee :: " + employee);
        }
    }

    @Override
    public boolean isExists(Employee employee, Long restaurantId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setLong(1, employee.getId());
            statement.setLong(2, restaurantId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if employee exists :: " + employee);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting employee :: " + id);
        }
    }

}
