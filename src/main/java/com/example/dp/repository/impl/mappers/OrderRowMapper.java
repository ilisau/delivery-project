package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.OrderStatus;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class OrderRowMapper implements RowMapper<Order> {

    @SneakyThrows
    public static Order mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("id"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            order.setDeliveredAt(resultSet.getTimestamp("delivered_at").toLocalDateTime());
            return order;
        } else {
            return null;
        }
    }

    @SneakyThrows
    public static List<Order> mapRows(ResultSet resultSet) {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("id"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            order.setDeliveredAt(resultSet.getTimestamp("delivered_at").toLocalDateTime());
            orders.add(order);
        }
        return orders;
    }
}
