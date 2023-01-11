package com.solvd.dp.repository.impl;

import com.solvd.dp.config.DataSourceConfig;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.repository.OrderRepository;
import com.solvd.dp.repository.impl.mappers.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private static final String SET_COURIER_BY_ID = "UPDATE orders SET courier_id = ? WHERE id = ?";

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
            SELECT DISTINCT id as order_id
            FROM orders o
                     JOIN users_orders uo on o.id = uo.order_id
            WHERE uo.user_id = ?""";
    private static final String GET_ALL_BY_ADDRESS_ID = """
            SELECT DISTINCT id as order_id
            FROM orders o
            WHERE o.address_id = ?""";
    private static final String GET_ALL_BY_COURIER_ID = """
            SELECT DISTINCT id as order_id
            FROM orders o
            WHERE o.courier_id = ?""";
    private static final String GET_ALL_BY_RESTAURANT_ID = """
            SELECT DISTINCT o.id as order_id
            FROM orders o
                JOIN carts c on o.cart_id = c.id
                JOIN carts_items ci on c.id = ci.cart_id
                JOIN restaurants_items ri on ci.item_id = ri.item_id
            WHERE ri.restaurant_id = ?""";
    private static final String GET_ALL_BY_RESTAURANT_ID_AND_STATUS = """
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
                     JOIN restaurants_items ri on i.id = ri.item_id
            WHERE ri.restaurant_id = ?
            AND o.status = ?
            ORDER BY o.id""";
    private static final String SAVE_BY_ID = "UPDATE orders SET courier_id = ?, status = ?, delivered_at = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO orders (address_id, cart_id, status, created_at) VALUES (?, ?, ?, ?)";
    private static final String CHANGE_STATUS = "UPDATE orders SET status = ? WHERE id = ?";
    private static final String IS_ORDER_ASSIGNED = "SELECT courier_id IS NOT NULL FROM orders WHERE id = ?";
    private static final String ASSIGN_ORDER = "UPDATE orders SET courier_id = ? WHERE id = ?";
    private static final String UPDATE_STATUS = "UPDATE orders SET status = ? WHERE id = ?";
    private static final String SET_DELIVERED_AT = "UPDATE orders SET delivered_at = ? WHERE id = ?";
    private static final String ADD_ORDER = "INSERT INTO users_orders (user_id, order_id) VALUES (?, ?)";
    private static final String DELETE_ORDER = "DELETE FROM users_orders WHERE user_id = ? AND order_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM orders WHERE id = ?";
    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<Order> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
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
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_USER_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (ordersResultSet.next()) {
                orders.add(findById(ordersResultSet.getLong("order_id")).get());
            }
            return orders;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by user id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByAddressId(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_ADDRESS_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (ordersResultSet.next()) {
                orders.add(findById(ordersResultSet.getLong("order_id")).get());
            }
            return orders;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by address id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByCourierId(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_COURIER_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (ordersResultSet.next()) {
                orders.add(findById(ordersResultSet.getLong("order_id")).get());
            }
            return orders;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by courier id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByRestaurantId(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_RESTAURANT_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            ResultSet ordersResultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (ordersResultSet.next()) {
                orders.add(findById(ordersResultSet.getLong("order_id")).get());
            }
            return orders;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by restaurant id :: " + id);
        }
    }

    @Override
    public List<Order> getAllByRestaurantIdAndStatus(Long id, OrderStatus status) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_RESTAURANT_ID_AND_STATUS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            statement.setString(2, status.name());
            ResultSet ordersResultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (ordersResultSet.next()) {
                orders.add(findById(ordersResultSet.getLong("order_id")).get());
            }
            return orders;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting orders by restaurant id :: " + id);
        }
    }

    @Override
    public Order save(Order order) {
        try {
            Connection connection = dataSourceConfig.getConnection();
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
    public Order create(Order order) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            order.setStatus(OrderStatus.ORDERED);
            order.setCreatedAt(LocalDateTime.now());
            statement.setLong(1, order.getAddress().getId());
            statement.setLong(2, order.getCart().getId());
            statement.setString(3, order.getStatus().name());
            statement.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            order.setId(key.getLong(1));
            return order;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating order :: " + order);
        }
    }

    @Override
    public void changeStatus(Long id, OrderStatus status) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_STATUS);
            statement.setString(1, status.name());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while changing order status :: " + id);
        }
    }

    @Override
    public void setCourierById(Long id, Long courierId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SET_COURIER_BY_ID);
            statement.setLong(1, courierId);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while setting courier to order :: " + id);
        }
    }

    @Override
    public boolean isOrderAssigned(Long orderId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_ORDER_ASSIGNED);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if order is assigned :: " + orderId);
        }
    }

    @Override
    public void assignOrder(Long orderId, Long courierId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN_ORDER);
            statement.setLong(1, courierId);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while assigning order :: " + orderId);
        }
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS);
            statement.setString(1, status.name());
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while updating order status :: " + orderId);
        }
    }

    @Override
    public void setDelivered(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SET_DELIVERED_AT);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while setting order delivered :: " + id);
        }
    }

    @Override
    public void addOrderById(Long userId, Long orderId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ORDER);
            statement.setLong(1, userId);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding order to user with id :: " + userId);
        }
    }

    @Override
    public void deleteOrderById(Long userId, Long orderId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ORDER);
            statement.setLong(1, userId);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting order from user with id :: " + userId);
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
            throw new ResourceMappingException("Exception while deleting order with id :: " + id);
        }
    }

}
