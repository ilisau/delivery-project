package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.OrderStatus;
import com.example.dp.repository.OrderRepository;
import com.example.dp.repository.impl.mappers.OrderRowMapper;
import com.example.dp.web.dto.mapper.CreateOrderMapper;
import com.example.dp.web.dto.user.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String GET_ALL_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?";
    private static final String GET_ALL_BY_ADDRESS_ID = "SELECT * FROM orders WHERE address_id = ?";
    private static final String GET_ALL_BY_RESTAURANT_ID = "SELECT * FROM orders WHERE restaurant_id = ?";
    private static final String GET_ALL_BY_COURIER_ID = "SELECT * FROM orders WHERE courier_id = ?";
    private static final String SAVE_BY_ID = "UPDATE orders SET courier_id = ?, status = ?, delivered_at = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO orders (user_id, address_id, restaurant_id, cart_id, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM orders WHERE id = ?";

    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            return Optional.of(OrderRowMapper.mapRow(ordersResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting order by id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByUserId(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            return OrderRowMapper.mapRows(ordersResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by user id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByAddressId(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_ADDRESS_ID);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            return OrderRowMapper.mapRows(ordersResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by address id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByRestaurantId(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_RESTAURANT_ID);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            return OrderRowMapper.mapRows(ordersResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by restaurant id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByCourierId(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_COURIER_ID);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            return OrderRowMapper.mapRows(ordersResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by courier id :: " + id);
        }
    }

    @Override
    public Order save(Order order) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setLong(1, order.getCourier().getId());
            statement.setString(2, order.getStatus().toString());
            statement.setTimestamp(3, Timestamp.valueOf(order.getDeliveredAt()));
            statement.setLong(4, order.getId());
            statement.executeUpdate();
            return order;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving order :: " + order);
        }
    }

    @Override
    public Order create(CreateOrderDto createOrderDto) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            Order order = CreateOrderMapper.INSTANCE.toEntity(createOrderDto);
            order.setStatus(OrderStatus.ORDERED);
            order.setCreatedAt(LocalDateTime.now());
            statement.setLong(1, createOrderDto.getUserId());
            statement.setLong(2, createOrderDto.getAddressId());
            statement.setLong(3, createOrderDto.getRestaurantId());
            statement.setLong(4, createOrderDto.getCartId());
            statement.setString(5, order.getStatus().name());
            statement.setTimestamp(6, Timestamp.valueOf(order.getCreatedAt()));
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            order.setId(key.getLong(1));
            return order;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating order :: " + createOrderDto);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting order with id :: " + id);
        }
    }
}
