package com.solvd.dp.repository.impl;

import com.solvd.dp.config.DataSourceConfig;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.repository.AddressRepository;
import com.solvd.dp.repository.impl.mappers.AddressRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private static final String FIND_BY_ID = """
            SELECT id as address_id,
                   street_name as address_street_name,
                   house_number as address_house_number,
                   floor_number as address_floor_number,
                   flat_number as address_flat_number
            FROM addresses
            WHERE id = ?""";
    private static final String FIND_ALL_BY_USER_ID = """
            SELECT id as address_id,
                   street_name as address_street_name,
                   house_number as address_house_number,
                   floor_number as address_floor_number,
                   flat_number as address_flat_number
            FROM users_addresses
                   JOIN addresses ON users_addresses.address_id = addresses.id
            WHERE user_id = ?""";
    private static final String FIND_ALL_BY_RESTAURANT_ID = """
            SELECT id as address_id,
                   street_name as address_street_name,
                   house_number as address_house_number,
                   floor_number as address_floor_number,
                   flat_number as address_flat_number
            FROM restaurants_addresses
                   JOIN addresses ON restaurants_addresses.address_id = addresses.id
            WHERE restaurant_id = ?""";
    private static final String SAVE_BY_ID = "UPDATE addresses SET street_name = ?, house_number = ?, floor_number = ?, flat_number = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO addresses (street_name, house_number, floor_number, flat_number) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM addresses WHERE id = ?";
    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<Address> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(AddressRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting address by id :: " + id);
        }
    }

    @Override
    public List<Address> getAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return AddressRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting addresses by user id :: " + userId);
        }
    }

    @Override
    public List<Address> getAllByRestaurantId(Long restaurantId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID);
            statement.setLong(1, restaurantId);
            try (ResultSet rs = statement.executeQuery()) {
                return AddressRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting addresses by restaurant id :: " + restaurantId);
        }
    }

    @Override
    public Address save(Address address) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setString(1, address.getStreetName());
            statement.setInt(2, address.getHouseNumber());
            if (address.getFloorNumber() != null) {
                statement.setInt(3, address.getFloorNumber());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (address.getFlatNumber() != null) {
                statement.setInt(4, address.getFlatNumber());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.setLong(5, address.getId());
            statement.executeUpdate();
            return address;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving address :: " + address);
        }
    }

    @Override
    public Address create(Address address) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getStreetName());
            statement.setInt(2, address.getHouseNumber());
            if (address.getFloorNumber() != null) {
                statement.setInt(3, address.getFloorNumber());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (address.getFlatNumber() != null) {
                statement.setInt(4, address.getFlatNumber());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                address.setId(key.getLong(1));
            }
            return address;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating address :: " + address);
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
            throw new ResourceMappingException("Exception while deleting address by id :: " + id);
        }
    }

}
