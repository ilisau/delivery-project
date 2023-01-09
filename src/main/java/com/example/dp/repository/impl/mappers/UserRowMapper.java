package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.user.Address;
import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.Order;
import com.example.dp.domain.user.User;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public abstract class UserRowMapper implements RowMapper<User> {

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        User user = new User();
        Cart cart = CartRowMapper.mapRow(resultSet);
        resultSet.beforeFirst();
        List<Address> addresses = AddressRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        List<Order> orders = OrderRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("user_id"));
            if (!resultSet.wasNull()) {
                user.setName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
                user.setPhoneNumber(resultSet.getString("user_phone_number"));
                user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                user.setCart(cart);
                user.setAddresses(addresses);
                user.setOrders(orders);
                return user;
            }
        }
        return null;
    }
}
