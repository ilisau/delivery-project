package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.repository.RestaurantRepository;
import com.example.dp.repository.impl.mappers.RestaurantRowMapper;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.mapper.CreateRestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT * FROM restaurants WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM restaurants WHERE name = ?";
    private static final String SAVE_BY_ID = "UPDATE restaurants SET name = ?, description = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO restaurants (name, description) VALUES (?, ?)";
    private static final String ADD_EMPLOYEE_BY_ID = "INSERT INTO restaurants_employees (restaurant_id, employee_id) VALUES (?, ?)";
    private static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM restaurants_employees WHERE restaurant_id = ? AND employee_id = ?";
    private static final String ADD_ITEM_BY_ID = "INSERT INTO restaurants_items (restaurant_id, item_id) VALUES (?, ?)";
    private static final String DELETE_ITEM_BY_ID = "DELETE FROM restaurants_items WHERE restaurant_id = ? AND item_id = ?";
    private static final String ADD_ADDRESS_BY_ID = "INSERT INTO restaurants_addresses (restaurant_id, address_id) VALUES (?, ?)";
    private static final String DELETE_ADDRESS_BY_ID = "DELETE FROM restaurants_addresses WHERE restaurant_id = ? AND address_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM restaurants WHERE id = ?";

    @Override
    public Optional<Restaurant> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet restaurantsResultSet = statement.executeQuery();
            return Optional.of(RestaurantRowMapper.mapRow(restaurantsResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting restaurant by id :: " + id);
        }
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            ResultSet restaurantsResultSet = statement.executeQuery();
            return Optional.of(RestaurantRowMapper.mapRow(restaurantsResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting restaurant by name :: " + name);
        }
    }

    @Override
    public Restaurant save(Restaurant restaurantDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID)) {
            statement.setString(1, restaurantDto.getName());
            statement.setString(2, restaurantDto.getDescription());
            statement.setLong(3, restaurantDto.getId());
            statement.executeUpdate();
            return restaurantDto;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving restaurant :: " + restaurantDto);
        }
    }

    @Override
    public Restaurant create(CreateRestaurantDto dto) {
        //todo set addresses
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, dto.getName());
            statement.setString(2, dto.getDescription());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            Restaurant restaurant = CreateRestaurantMapper.INSTANCE.toEntity(dto);
            restaurant.setId(key.getLong(1));
            return restaurant;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating restaurant :: " + dto);
        }
    }

    @Override
    public void addEmployeeById(Long id, Long employeeId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_EMPLOYEE_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding employee to restaurant :: " + id);
        }
    }

    @Override
    public void deleteEmployeeById(Long id, Long employeeId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting employee from restaurant :: " + id);
        }
    }

    @Override
    public void addItemById(Long id, Long itemId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ITEM_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding item to restaurant :: " + id);
        }
    }

    @Override
    public void deleteItemById(Long id, Long itemId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting item from restaurant :: " + id);
        }
    }

    @Override
    public void addAddressById(Long id, Long addressId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ADDRESS_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding address to restaurant :: " + id);
        }
    }

    @Override
    public void deleteAddressById(Long id, Long addressId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting address from restaurant :: " + id);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting restaurant by id :: " + id);
        }
    }
}
