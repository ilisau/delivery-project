package com.solvd.dp.repository.impl.mappers;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.user.Cart;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.Map;

public abstract class CartRowMapper implements RowMapper<Cart> {

    @SneakyThrows
    public static Cart mapRow(ResultSet resultSet) {
        Cart cart = new Cart();
        Map<Item, Long> items = ItemRowMapper.mapRowsToMap(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            cart.setId(resultSet.getLong("cart_id"));
            if (!resultSet.wasNull()) {
                cart.setItems(items);
                return cart;
            }
        }
        return null;
    }

}
