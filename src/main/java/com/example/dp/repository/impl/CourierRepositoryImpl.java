package com.example.dp.repository.impl;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.repository.CourierRepository;
import com.example.dp.repository.impl.mappers.CourierRowMapper;
import com.example.dp.web.dto.courier.CreateCourierDto;
import com.example.dp.web.dto.mapper.CreateCourierMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierRepositoryImpl implements CourierRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT * FROM couriers WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM couriers";
    private static final String FIND_ALL_BY_STATUS = "SELECT * FROM couriers WHERE status = ?";
    private static final String UPDATE_BY_ID = "UPDATE couriers SET first_name = ?, last_name = ?, status = ?, phone_number = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO couriers (first_name, last_name, created_at, last_active_at, status, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM couriers WHERE id = ?";

    @Override
    public Optional<Courier> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet courierResultSet = statement.executeQuery();
            return Optional.of(CourierRowMapper.mapRow(courierResultSet));
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting courier by id :: " + id);
        }
    }

    @Override
    public List<Courier> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet courierResultSet = statement.executeQuery();
            return CourierRowMapper.mapRows(courierResultSet);
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting all couriers");
        }
    }

    @Override
    public List<Courier> getAllByStatus(CourierStatus status) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_STATUS)) {
            statement.setString(1, status.name());
            ResultSet courierResultSet = statement.executeQuery();
            return CourierRowMapper.mapRows(courierResultSet);
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting all couriers by status :: " + status);
        }
    }

    @Override
    public Courier save(Courier courier) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID)) {
            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setString(3, courier.getStatus().name());
            statement.setString(4, courier.getPhoneNumber());
            statement.setLong(5, courier.getId());
            statement.executeUpdate();
            return courier;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving courier :: " + courier);
        }
    }

    @Override
    public Courier create(CreateCourierDto createCourierDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            Courier courier = CreateCourierMapper.INSTANCE.toEntity(createCourierDto);
            courier.setCreatedAt(LocalDate.now());
            courier.setLastActiveAt(LocalDateTime.now());
            courier.setStatus(CourierStatus.AVAILABLE);

            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setObject(3, courier.getCreatedAt());
            statement.setObject(4, courier.getLastActiveAt());
            statement.setString(5, courier.getStatus().name());
            statement.setString(6, createCourierDto.getPhoneNumber());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            courier.setId(key.getLong(1));
            return courier;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating courier :: " + createCourierDto);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while deleting courier by id :: " + id);
        }
    }
}
