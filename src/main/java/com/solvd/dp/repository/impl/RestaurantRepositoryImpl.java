package com.solvd.dp.repository.impl;

import com.solvd.dp.config.DataSourceConfig;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.repository.RestaurantRepository;
import com.solvd.dp.repository.impl.mappers.RestaurantRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private static final String GET_ALL = """
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
                   i.id           as item_id,
                   i.name         as item_name,
                   i.description  as item_description,
                   i.type         as item_type,
                   i.price        as item_price,
                   i.available    as item_available
            FROM restaurants r
                     LEFT JOIN restaurants_addresses ra on r.id = ra.restaurant_id
                     LEFT JOIN addresses a on ra.address_id = a.id
                     LEFT JOIN restaurants_employees re on r.id = re.restaurant_id
                     LEFT JOIN employees e on re.employee_id = e.id
                     LEFT JOIN restaurants_items on r.id = restaurants_items.restaurant_id
                     LEFT JOIN items i on restaurants_items.item_id = i.id""";
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
    private static final String IS_EXISTS = "SELECT EXISTS(SELECT 1 FROM restaurants WHERE name = ?)";
    private static final String IS_EMPLOYEE_EXISTS = "SELECT EXISTS(SELECT 1 FROM restaurants_employees WHERE restaurant_id = ? AND employee_id = ?)";
    private static final String SAVE_BY_ID = "UPDATE restaurants SET name = ?, description = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO restaurants (name, description) VALUES (?, ?)";
    private static final String ADD_EMPLOYEE_BY_ID = "INSERT INTO restaurants_employees (restaurant_id, employee_id) VALUES (?, ?)";
    private static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM restaurants_employees WHERE restaurant_id = ? AND employee_id = ?";
    private static final String ADD_ITEM_BY_ID = "INSERT INTO restaurants_items (restaurant_id, item_id) VALUES (?, ?)";
    private static final String DELETE_ITEM_BY_ID = "DELETE FROM restaurants_items WHERE restaurant_id = ? AND item_id = ?";
    private static final String ADD_ADDRESS_BY_ID = "INSERT INTO restaurants_addresses (restaurant_id, address_id) VALUES (?, ?)";
    private final DataSourceConfig dataSourceConfig;
    private static final String DELETE_ADDRESS_BY_ID = "DELETE FROM restaurants_addresses WHERE restaurant_id = ? AND address_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM restaurants WHERE id = ?";

    @Override
    public List<Restaurant> getAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();
            return RestaurantRowMapper.mapRows(resultSet);
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
            ResultSet restaurantsResultSet = statement.executeQuery();
            return Optional.ofNullable(RestaurantRowMapper.mapRow(restaurantsResultSet));
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
            ResultSet restaurantsResultSet = statement.executeQuery();
            return Optional.ofNullable(RestaurantRowMapper.mapRow(restaurantsResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting restaurant by name :: " + name);
        }
    }

    @Override
    public Restaurant save(Restaurant restaurantDto) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
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
    public Restaurant create(Restaurant restaurant) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getDescription());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            restaurant.setId(key.getLong(1));
            return restaurant;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating restaurant :: " + restaurant);
        }
    }

    @Override
    public void addEmployeeById(Long id, Long employeeId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_EMPLOYEE_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding employee to restaurant :: " + id);
        }
    }

    @Override
    public void deleteEmployeeById(Long id, Long employeeId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, employeeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting employee from restaurant :: " + id);
        }
    }

    @Override
    public void addItemById(Long id, Long itemId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ITEM_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding item to restaurant :: " + id);
        }
    }

    @Override
    public void deleteItemById(Long id, Long itemId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting item from restaurant :: " + id);
        }
    }

    @Override
    public void addAddressById(Long id, Long addressId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ADDRESS_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding address to restaurant :: " + id);
        }
    }

    @Override
    public void deleteAddressById(Long id, Long addressId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS_BY_ID);
            statement.setLong(1, id);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting address from restaurant :: " + id);
        }
    }

    @Override
    public boolean isExists(Restaurant restaurant) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, restaurant.getName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if restaurant exists :: " + restaurant);
        }
    }

    @Override
    public boolean isEmployeeExists(Long id, Long employeeId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EMPLOYEE_EXISTS);
            statement.setLong(1, id);
            statement.setLong(2, employeeId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if employee exists in restaurant :: " + id);
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
