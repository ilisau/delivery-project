package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Address;
import com.example.dp.repository.AddressRepository;
import com.example.dp.repository.impl.mappers.AddressRowMapper;
import com.example.dp.web.dto.user.CreateAddressDto;
import com.example.dp.web.mapper.CreateAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT id, street_name, house_number, floor_number, flat_number FROM addresses WHERE id = ?";
    private static final String FIND_ALL_BY_USER_ID = "SELECT id, street_name, house_number, floor_number, flat_number FROM users_addresses JOIN addresses ON users_addresses.address_id = addresses.id WHERE user_id = ?";
    private static final String FIND_ALL_BY_RESTAURANT_ID = "SELECT id, street_name, house_number, floor_number, flat_number FROM restaurants_addresses JOIN addresses ON restaurants_addresses.address_id = addresses.id WHERE restaurant_id = ?";
    private static final String SAVE_BY_ID = "UPDATE addresses SET street_name = ?, house_number = ?, floor_number = ?, flat_number = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO addresses (street_name, house_number, floor_number, flat_number) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM addresses WHERE id = ?";

    @Override
    public Optional<Address> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet addressResultSet = statement.executeQuery();
            return Optional.ofNullable(AddressRowMapper.mapRow(addressResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting address by id :: " + id);
        }
    }

    @Override
    public List<Address> getAllByUserId(Long userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID)) {
            statement.setLong(1, userId);
            ResultSet addressResultSet = statement.executeQuery();
            return AddressRowMapper.mapRows(addressResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting addresses by user id :: " + userId);
        }
    }

    @Override
    public List<Address> getAllByRestaurantId(Long restaurantId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID)) {
            statement.setLong(1, restaurantId);
            ResultSet addressResultSet = statement.executeQuery();
            return AddressRowMapper.mapRows(addressResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting addresses by restaurant id :: " + restaurantId);
        }
    }

    @Override
    public Address save(Address address) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID)) {
            statement.setString(1, address.getStreetName());
            statement.setInt(2, address.getHouseNumber());
            statement.setInt(3, address.getFloorNumber());
            statement.setInt(4, address.getFlatNumber());
            statement.setLong(5, address.getId());
            statement.executeUpdate();
            return address;
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while saving address :: " + address);
        }
    }

    @Override
    public Address create(CreateAddressDto addressDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, addressDto.getStreetName());
            statement.setInt(2, addressDto.getHouseNumber());
            statement.setInt(3, addressDto.getFloorNumber());
            statement.setInt(4, addressDto.getFlatNumber());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            Address address = CreateAddressMapper.INSTANCE.toEntity(addressDto);
            address.setId(key.getLong(1));
            return address;
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while creating address :: " + addressDto);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while deleting address by id :: " + id);
        }
    }
}
