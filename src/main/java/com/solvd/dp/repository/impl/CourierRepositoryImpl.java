package com.solvd.dp.repository.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.repository.CourierRepository;
import com.solvd.dp.repository.DataSourceConfig;
import com.solvd.dp.repository.mappers.CourierRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourierRepositoryImpl implements CourierRepository {

    private final DataSourceConfig dataSourceConfig;

    private static final String FIND_BY_ORDER_ID = """
            SELECT c.id            as courier_id,
                    c.first_name     as courier_first_name,
                    c.last_name      as courier_last_name,
                    c.phone_number   as courier_phone_number,
                    c.created_at     as courier_created_at,
                    c.last_active_at as courier_last_active_at,
                    c.status         as courier_status
            FROM couriers c
            LEFT JOIN orders o on c.id = o.courier_id
            WHERE o.id = ?""";
    private static final String FIND_BY_ID = """
            SELECT id         as courier_id,
                   first_name as courier_first_name,
                   last_name as courier_last_name,
                   phone_number as courier_phone_number,
                   created_at as courier_created_at,
                   last_active_at as courier_last_active_at,
                   status     as courier_status
            FROM couriers
            WHERE id = ?""";
    private static final String IS_EXISTS = "SELECT id FROM couriers WHERE (first_name = ? AND last_name = ?) OR phone_number = ?";
    private static final String FIND_ALL = """
            SELECT id         as courier_id,
                    first_name as courier_first_name,
                    last_name as courier_last_name,
                    phone_number as courier_phone_number,
                    created_at as courier_created_at,
                    last_active_at as courier_last_active_at,
                    status     as courier_status
            FROM couriers""";
    private static final String FIND_ALL_BY_STATUS = """
            SELECT id         as courier_id,
                               first_name as courier_first_name,
                               last_name as courier_last_name,
                               phone_number as courier_phone_number,
                               created_at as courier_created_at,
                               last_active_at as courier_last_active_at,
                               status     as courier_status
                        FROM couriers
                        WHERE status = ?""";
    private static final String UPDATE_BY_ID = "UPDATE couriers SET first_name = ?, last_name = ?, status = ?, phone_number = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO couriers (first_name, last_name, created_at, last_active_at, status, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM couriers WHERE id = ?";

    @Override
    public Optional<Courier> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(CourierRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting courier by id :: " + id);
        }
    }

    @Override
    public Optional<Courier> findByOrderId(Long orderId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ORDER_ID);
            statement.setLong(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(CourierRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting courier by order id :: " + orderId);
        }
    }

    @Override
    public List<Courier> getAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            try (ResultSet rs = statement.executeQuery()) {
                return CourierRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting all couriers");
        }
    }

    @Override
    public List<Courier> getAllByStatus(CourierStatus status) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_STATUS);
            statement.setString(1, status.name());
            try (ResultSet rs = statement.executeQuery()) {
                return CourierRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting all couriers by status :: " + status);
        }
    }

    @Override
    public void update(Courier courier) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID);
            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setString(3, courier.getStatus().name());
            statement.setString(4, courier.getPhoneNumber());
            statement.setLong(5, courier.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving courier :: " + courier);
        }
    }

    @Override
    public void create(Courier courier) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            courier.setCreatedAt(LocalDateTime.now());
            courier.setLastActiveAt(LocalDateTime.now());
            courier.setStatus(CourierStatus.AVAILABLE);

            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setObject(3, courier.getCreatedAt());
            statement.setObject(4, courier.getLastActiveAt());
            statement.setString(5, courier.getStatus().name());
            statement.setString(6, courier.getPhoneNumber());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                courier.setId(key.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating courier :: " + courier);
        }
    }

    @Override
    public boolean exists(Courier courier) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setString(3, courier.getPhoneNumber());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong(1);
                    if (!Objects.equals(courier.getId(), id)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if courier exists :: " + courier);
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
            throw new ResourceMappingException("Exception while deleting courier by id :: " + id);
        }
    }

}
