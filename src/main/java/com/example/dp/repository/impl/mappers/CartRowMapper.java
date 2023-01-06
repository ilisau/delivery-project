package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.user.Cart;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class CartRowMapper implements RowMapper<Cart> {

    @SneakyThrows
    public static Cart mapRow(ResultSet resultSet) {
        resultSet.next();
        Cart cart = new Cart();
        cart.setId(resultSet.getLong("id"));
        return cart;
    }

    @SneakyThrows
    public static List<Cart> mapRows(ResultSet resultSet) {
        List<Cart> carts = new ArrayList<>();
        while (resultSet.next()) {
            Cart cart = new Cart();
            cart.setId(resultSet.getLong("id"));
            carts.add(cart);
        }
        return carts;
    }
}
