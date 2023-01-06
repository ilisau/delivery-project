package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.restaurant.Employee;
import com.example.dp.domain.restaurant.EmployeePosition;
import com.example.dp.repository.EmployeeRepository;
import com.example.dp.repository.impl.mappers.EmployeeRowMapper;
import com.example.dp.web.dto.mapper.CreateEmployeeMapper;
import com.example.dp.web.dto.restaurant.CreateEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT * FROM employees WHERE id = ?";
    private static final String FIND_ALL_BY_RESTAURANT_ID = "SELECT employees.id, employees.name, employees.position FROM restaurants_employees JOIN employees ON employee_id = employees.id WHERE restaurants_employees.restaurant_id = ?";
    private static final String FIND_ALL_BY_RESTAURANT_ID_AND_POSITION = "SELECT * FROM restaurants_employees JOIN employees ON employee_id = employees.id WHERE restaurants_employees.restaurant_id = ? AND employees.position = ?";
    private static final String SAVE_BY_ID = "UPDATE employees SET name = ?, position = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO employees (name, position) VALUES (?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM employees WHERE id = ?";

    @Override
    public Optional<Employee> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet employeeResultSet = statement.executeQuery();
            return Optional.of(EmployeeRowMapper.mapRow(employeeResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employee by id :: " + id);
        }
    }

    @Override
    public List<Employee> getAllByRestaurantId(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID)) {
            statement.setLong(1, id);
            ResultSet employeeResultSet = statement.executeQuery();
            return EmployeeRowMapper.mapRows(employeeResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employees by restaurant id :: " + id);
        }
    }

    @Override
    public List<Employee> getAllByRestaurantIdAndPosition(Long id, EmployeePosition position) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID_AND_POSITION)) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID)) {
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
    public Employee create(CreateEmployeeDto createEmployeeDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, createEmployeeDto.getName());
            statement.setString(2, createEmployeeDto.getPosition().name());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            Employee employee = CreateEmployeeMapper.INSTANCE.toEntity(createEmployeeDto);
            employee.setId(key.getLong(1));
            return employee;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating employee :: " + createEmployeeDto);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting employee :: " + id);
        }
    }
}
