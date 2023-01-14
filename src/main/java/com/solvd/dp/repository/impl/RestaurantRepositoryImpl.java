package com.solvd.dp.repository.impl;

import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.repository.DataSourceConfig;
import com.solvd.dp.repository.RestaurantRepository;
import com.solvd.dp.repository.mappers.RestaurantRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final DataSourceConfig dataSourceConfig;

    private static final String GET_ALL = """
            SELECT id as restaurant_id
            FROM restaurants""";
    private static final String FIND_BY_ID = """
            SELECT r.id           as restaurant_id,
                                  r.name         as restaurant_name,
                                  r.description  as restaurant_description,
                                  a.id           as address_id,
                                  a.street_name  as address_street_name,
                                  a.house_number as address_house_number,
                                  a.floor_number as address_floor_number,
                                  a.flat_number  as address_flat_number,
                                  e.id           as employee_id,
                                  e.name         as employee_name,
                                  e.position     as employee_position,
                                  i.id as item_id,
                                  i.name as item_name,
                                  i.description as item_description,
                                  i.type as item_type,
                                  i.price as item_price,
                                  i.available as item_available
                           FROM restaurants r
                                    LEFT JOIN restaurants_addresses ra on r.id = ra.restaurant_id
                                    LEFT JOIN addresses a on ra.address_id = a.id
                                    LEFT JOIN restaurants_employees re on r.id = re.restaurant_id
                                    LEFT JOIN employees e on re.employee_id = e.id
                                    LEFT JOIN restaurants_items on r.id = restaurants_items.restaurant_id
                                    LEFT JOIN items i on restaurants_items.item_id = i.id
                           WHERE r.id = ?""";
    private static final String FIND_BY_NAME = """
            SELECT r.id           as restaurant_id,
                   r.name         as restaurant_name,
                   r.description  as restaurant_description,
                   a.id           as address_id,
                   a.street_name  as address_street_name,
                   a.house_number as address_house_number,
                   a.floor_number as address_floor_number,
                   a.flat_number  as address_flat_number,
                   e.id           as employee_id,
                   e.name         as employee_name,
                   e.position     as employee_position,
                   i.id as item_id,
                   i.name as item_name,
                   i.description as item_description,
                   i.type as item_type,
                   i.price as item_price,
                   i.available as item_available
            FROM restaurants r
                     LEFT JOIN restaurants_addresses ra on r.id = ra.restaurant_id
                     LEFT JOIN addresses a on ra.address_id = a.id
                     LEFT JOIN restaurants_employees re on r.id = re.restaurant_id
                     LEFT JOIN employees e on re.employee_id = e.id
                     LEFT JOIN restaurants_items on r.id = restaurants_items.restaurant_id
                     LEFT JOIN items i on restaurants_items.item_id = i.id
            WHERE r.name = ?""";
    private static final String IS_EXISTS = "SELECT id FROM restaurants WHERE name = ?";
    private static final String IS_EMPLOYEE_EXISTS = "SELECT EXISTS(SELECT 1 FROM restaurants_employees WHERE restaurant_id = ? AND employee_id = ?)";
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
    public List<Restaurant> getAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL);
            try (ResultSet rs = statement.executeQuery()) {
                List<Restaurant> restaurants = new ArrayList<>();
                while (rs.next()) {
                    restaurants.add(findById(rs.getLong("restaurant_id")).get());
                }
                return restaurants;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting all restaurants");
        }
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(RestaurantRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting restaurant by id :: " + id);
        }
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(RestaurantRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting restaurant by name :: " + name);
        }
    }

    @Override
    public void update(Restaurant restaurantDto) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setString(1, restaurantDto.getName());
            statement.setString(2, restaurantDto.getDescription());
            statement.setLong(3, restaurantDto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving restaurant :: " + restaurantDto);
        }
    }

    @Override
    public void create(Restaurant restaurant) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getDescription());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                restaurant.setId(key.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating restaurant :: " + restaurant);
        }
    }

    @Override
    public void addEmployeeById(Long restaurantId, Long employeeId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_EMPLOYEE_BY_ID);
            statement.setLong(1, restaurantId);
            statement.setLong(2, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding employee to restaurant :: " + restaurantId);
        }
    }

    @Override
    public void deleteEmployeeById(Long restaurantId, Long employeeId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID);
            statement.setLong(1, restaurantId);
            statement.setLong(2, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting employee from restaurant :: " + restaurantId);
        }
    }

    @Override
    public void addItemById(Long restaurantId, Long itemId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ITEM_BY_ID);
            statement.setLong(1, restaurantId);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding item to restaurant :: " + restaurantId);
        }
    }

    @Override
    public void deleteItemById(Long restaurantId, Long itemId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID);
            statement.setLong(1, restaurantId);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting item from restaurant :: " + restaurantId);
        }
    }

    @Override
    public void addAddressById(Long restaurantId, Long addressId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ADDRESS_BY_ID);
            statement.setLong(1, restaurantId);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding address to restaurant :: " + restaurantId);
        }
    }

    @Override
    public void deleteAddressById(Long restaurantId, Long addressId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS_BY_ID);
            statement.setLong(1, restaurantId);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting address from restaurant :: " + restaurantId);
        }
    }

    @Override
    public boolean exists(Restaurant restaurant) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, restaurant.getName());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    return !Objects.equals(restaurant.getId(), id);
                }
                return false;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if restaurant exists :: " + restaurant);
        }
    }

    @Override
    public boolean employeeExists(Long restaurantId, Long employeeId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EMPLOYEE_EXISTS);
            statement.setLong(1, restaurantId);
            statement.setLong(2, employeeId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if employee exists in restaurant :: " + restaurantId);
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
            throw new ResourceMappingException("Exception while deleting restaurant by id :: " + id);
        }
    }

}
