package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.OrderStatus;
import com.example.dp.repository.OrderRepository;
import com.example.dp.repository.impl.mappers.OrderRowMapper;
import com.example.dp.web.dto.user.CreateOrderDto;
import com.example.dp.web.mapper.CreateOrderMapper;
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

    private static final String FIND_BY_ID = """
            SELECT o.id         as order_id,
                   o.status     as order_status,
                   o.created_at as order_created_at,
                   o.delivered_at as order_delivered_at,
                   a.id         as address_id,
                   a.street_name as address_street_name,
                   a.house_number as address_house_number,
                   a.floor_number as address_floor_number,
                   a.flat_number as address_flat_number,
                   c.id         as courier_id,
                   c.first_name as courier_first_name,
                   c.last_name as courier_last_name,
                   c.last_active_at as courier_last_active_at,
                   c.phone_number as courier_phone_number,
                   c.status     as courier_status,
                   c.created_at as courier_created_at,
                   c2.id        as cart_id,
                   i.id         as item_id,
                   i.name as item_name,
                   i.description as item_description,
                   i.price as item_price,
                   i.type as item_type,
                   i.available as item_available,
                   ci.quantity as item_quantity
            FROM orders o
                     LEFT JOIN addresses a on o.address_id = a.id
                     LEFT JOIN couriers c on o.courier_id = c.id
                     LEFT JOIN carts c2 on o.cart_id = c2.id
                     LEFT JOIN carts_items ci on c2.id = ci.cart_id
                     LEFT JOIN items i on ci.item_id = i.id
            WHERE o.id = ?""";
    private static final String GET_ALL_BY_USER_ID = """
            SELECT o.id         as order_id,
                   o.status     as order_status,
                   o.created_at as order_created_at,
                   o.delivered_at as order_delivered_at,
                   a.id         as address_id,
                   a.street_name as address_street_name,
                   a.house_number as address_house_number,
                   a.floor_number as address_floor_number,
                   a.flat_number as address_flat_number,
                   c.id         as courier_id,
                   c.first_name as courier_first_name,
                   c.last_name as courier_last_name,
                   c.last_active_at as courier_last_active_at,
                   c.phone_number as courier_phone_number,
                   c.status     as courier_status,
                   c.created_at as courier_created_at,
                   c2.id        as cart_id,
                   i.id         as item_id,
                   i.name as item_name,
                   i.description as item_description,
                   i.price as item_price,
                   i.type as item_type,
                   i.available as item_available,
                   ci.quantity as item_quantity
            FROM orders o
                     LEFT JOIN addresses a on o.address_id = a.id
                     LEFT JOIN couriers c on o.courier_id = c.id
                     LEFT JOIN carts c2 on o.cart_id = c2.id
                     LEFT JOIN carts_items ci on c2.id = ci.cart_id
                     LEFT JOIN items i on ci.item_id = i.id
                     LEFT JOIN users_orders uo on o.id = uo.order_id
                     LEFT JOIN users u on uo.user_id = u.id
            WHERE u.id = ?""";
    private static final String GET_ALL_BY_ADDRESS_ID = """
            SELECT o.id             as order_id,
                   o.status         as order_status,
                   o.created_at     as order_created_at,
                   o.delivered_at   as order_delivered_at,
                   a.id             as address_id,
                   a.street_name    as address_street_name,
                   a.house_number   as address_house_number,
                   a.floor_number   as address_floor_number,
                   a.flat_number    as address_flat_number,
                   c.id             as courier_id,
                   c.first_name     as courier_first_name,
                   c.last_name      as courier_last_name,
                   c.last_active_at as courier_last_active_at,
                   c.phone_number   as courier_phone_number,
                   c.status         as courier_status,
                   c.created_at     as courier_created_at,
                   c2.id            as cart_id,
                   i.id             as item_id,
                   i.name           as item_name,
                   i.description    as item_description,
                   i.price          as item_price,
                   i.type           as item_type,
                   i.available      as item_available,
                   ci.quantity      as item_quantity
            FROM orders o
                     LEFT JOIN addresses a on o.address_id = a.id
                     LEFT JOIN couriers c on o.courier_id = c.id
                     LEFT JOIN carts c2 on o.cart_id = c2.id
                     LEFT JOIN carts_items ci on c2.id = ci.cart_id
                     LEFT JOIN items i on ci.item_id = i.id
            WHERE a.id = ?""";
    private static final String GET_ALL_BY_COURIER_ID = """
            SELECT o.id             as order_id,
                   o.status         as order_status,
                   o.created_at     as order_created_at,
                   o.delivered_at   as order_delivered_at,
                   a.id             as address_id,
                   a.street_name    as address_street_name,
                   a.house_number   as address_house_number,
                   a.floor_number   as address_floor_number,
                   a.flat_number    as address_flat_number,
                   c.id             as courier_id,
                   c.first_name     as courier_first_name,
                   c.last_name      as courier_last_name,
                   c.last_active_at as courier_last_active_at,
                   c.phone_number   as courier_phone_number,
                   c.status         as courier_status,
                   c.created_at     as courier_created_at,
                   c2.id            as cart_id,
                   i.id             as item_id,
                   i.name           as item_name,
                   i.description    as item_description,
                   i.price          as item_price,
                   i.type           as item_type,
                   i.available      as item_available,
                   ci.quantity      as item_quantity
            FROM orders o
                     LEFT JOIN addresses a on o.address_id = a.id
                     LEFT JOIN couriers c on o.courier_id = c.id
                     LEFT JOIN carts c2 on o.cart_id = c2.id
                     LEFT JOIN carts_items ci on c2.id = ci.cart_id
                     LEFT JOIN items i on ci.item_id = i.id
            WHERE c.id = ?""";
    private static final String SAVE_BY_ID = "UPDATE orders SET courier_id = ?, status = ?, delivered_at = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO orders (address_id, cart_id, status, created_at) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM orders WHERE id = ?";

    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            return Optional.ofNullable(OrderRowMapper.mapRow(ordersResultSet));
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
            statement.setLong(1, createOrderDto.getAddressId());
            statement.setLong(2, createOrderDto.getCartId());
            statement.setString(3, order.getStatus().name());
            statement.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
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
