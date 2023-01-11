package com.solvd.dp.repository.impl.mappers;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.Timestamp;

public abstract class OrderRowMapper implements RowMapper<Order> {

    @SneakyThrows
    public static Order mapRow(ResultSet resultSet) {
        Order order = new Order();
        Cart cart = CartRowMapper.mapRow(resultSet);
        resultSet.beforeFirst();
        Courier courier = CourierRowMapper.mapRow(resultSet);
        resultSet.beforeFirst();
        Address address = AddressRowMapper.mapRow(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            order.setId(resultSet.getLong("order_id"));
            if (!resultSet.wasNull()) {
                order.setStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setCreatedAt(resultSet.getTimestamp("order_created_at").toLocalDateTime());
                Timestamp timestamp = resultSet.getTimestamp("order_delivered_at");
                if (timestamp != null) {
                    order.setDeliveredAt(timestamp.toLocalDateTime());
                }
                order.setAddress(address);
                order.setCart(cart);
                order.setCourier(courier);
                return order;
            }
        }
        return null;
    }

}
