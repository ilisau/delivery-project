package com.solvd.dp.repository.impl;

import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.repository.DataSourceConfig;
import com.solvd.dp.repository.EmployeeRepository;
import com.solvd.dp.repository.mappers.EmployeeRowMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final DataSourceConfig dataSourceConfig;

    private static final String FIND_ALL_BY_RESTAURANT_ID = """
            SELECT employees.id       as employee_id,
                   employees.name     as employee_name,
                   employees.position as employee_position,
                   er.role            as employee_role
            FROM restaurants_employees
                   LEFT JOIN employees ON employee_id = employees.id
                   JOIN employees_roles er on employees.id = er.employee_id
            WHERE restaurants_employees.restaurant_id = ?""";
    private static final String FIND_BY_ID = """
            SELECT employees.id       as employee_id,
                               employees.name     as employee_name,
                               employees.position as employee_position,
                               er.role            as employee_role
                        FROM restaurants_employees
                                 LEFT JOIN employees ON employee_id = employees.id
                                 JOIN employees_roles er on employees.id = er.employee_id
                        WHERE employees.id = ?""";
    private static final String FIND_BY_EMAIL = """
            SELECT employees.id       as employee_id,
                               employees.name     as employee_name,
                               employees.position as employee_position,
                               er.role            as employee_role
                        FROM restaurants_employees
                                 LEFT JOIN employees ON employee_id = employees.id
                                 JOIN employees_roles er on employees.id = er.employee_id
                        WHERE employees.email = ?""";
    private static final String FIND_ALL_BY_RESTAURANT_ID_AND_POSITION = """
            SELECT employees.id       as employee_id,
                   employees.name     as employee_name,
                   employees.position as employee_position,
                   er.role            as employee_role
            FROM restaurants_employees
                   LEFT JOIN employees ON employee_id = employees.id
                   JOIN employees_roles er on employees.id = er.employee_id
            WHERE restaurants_employees.restaurant_id = ?
            AND employees.position = ?""";
    private static final String IS_EXISTS = """
            SELECT EXISTS(
                SELECT 1
                FROM restaurants_employees re
                JOIN employees e on re.employee_id = e.id
                WHERE e.name = ?
                  AND re.restaurant_id = ?
                )""";
    private static final String SAVE_ROLES = "INSERT INTO employees_roles (employee_id, role) VALUES (?, ?)";
    private static final String SAVE_BY_ID = "UPDATE employees SET name = ?, position = ?, email = ?, password = ? WHERE id = ?";
    private static final String IS_MANAGER = """
            SELECT EXISTS(
                SELECT 1
                FROM employees_roles
                         JOIN employees e on e.id = employees_roles.employee_id
                         JOIN restaurants_employees re on e.id = re.employee_id
                WHERE role = ?
                  AND restaurant_id = ?
                  AND e.id = ?
            )""";
    private static final String CREATE = "INSERT INTO employees (name, position, email, password) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM employees WHERE id = ?";

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(EmployeeRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employee by id :: " + id);
        }
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL);
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(EmployeeRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employee by username :: " + email);
        }
    }

    @Override
    public List<Employee> getAllByRestaurantId(Long restaurantId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID);
            statement.setLong(1, restaurantId);
            try (ResultSet rs = statement.executeQuery()) {
                return EmployeeRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employees by restaurant id :: " + restaurantId);
        }
    }

    @Override
    public List<Employee> getAllByRestaurantIdAndPosition(Long restaurantId, EmployeePosition position) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID_AND_POSITION);
            statement.setLong(1, restaurantId);
            statement.setString(2, position.name());
            try (ResultSet rs = statement.executeQuery()) {
                return EmployeeRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting employees by restaurant id and position :: " + restaurantId + " :: " + position);
        }
    }

    @Override
    public void update(Employee employee) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition().name());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getPassword());
            statement.setLong(5, employee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving employee :: " + employee);
        }
    }

    @Override
    public void create(Employee employee) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition().name());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getPassword());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                employee.setId(key.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating employee :: " + employee);
        }
    }

    @Override
    public void saveRoles(Long id, Set<Role> roles) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_ROLES);
            for (Role role : roles) {
                statement.setLong(1, id);
                statement.setString(2, role.name());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving roles for employee :: " + id);
        }
    }

    @Override
    public boolean exists(Employee employee, Long restaurantId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, employee.getName());
            statement.setLong(2, restaurantId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if employee exists :: " + employee);
        }
    }

    @Override
    public boolean isManager(Long employeeId, Long restaurantId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_MANAGER);
            statement.setString(1, Role.ROLE_MANAGER.name());
            statement.setLong(2, restaurantId);
            statement.setLong(3, employeeId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if employee is manager :: " + employeeId);
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
