package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Address;
import com.example.dp.repository.AddressRepository;
import com.example.dp.repository.impl.mappers.AddressRowMapper;
import com.example.dp.web.dto.mapper.CreateAddressMapper;
import com.example.dp.web.dto.user.CreateAddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT * FROM addresses WHERE id = ?";
    private static final String UPDATE_BY_ID = "UPDATE addresses SET street_name = ?, house_number = ?, floor_number = ?, flat_number = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO addresses (street_name, house_number, floor_number, flat_number) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM addresses WHERE id = ?";

    @Override
    public Optional<Address> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet addressResultSet = statement.executeQuery();
            return Optional.of(AddressRowMapper.mapRow(addressResultSet));
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting address by id :: " + id);
        }
    }

    @Override
    public Address save(Address address) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
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
