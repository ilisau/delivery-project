package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class ItemRowMapper implements RowMapper<Item> {

    @SneakyThrows
    public static Item mapRow(ResultSet resultSet) {
        resultSet.next();
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        item.setDescription(resultSet.getString("description"));
        item.setPrice(resultSet.getBigDecimal("price"));
        item.setType(ItemType.valueOf(resultSet.getString("type")));
        item.setAvailable(resultSet.getBoolean("available"));
        return item;
    }

    @SneakyThrows
    public static List<Item> mapRows(ResultSet resultSet) {
        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getLong("id"));
            item.setName(resultSet.getString("name"));
            item.setDescription(resultSet.getString("description"));
            item.setPrice(resultSet.getBigDecimal("price"));
            item.setType(ItemType.valueOf(resultSet.getString("type")));
            item.setAvailable(resultSet.getBoolean("available"));
            items.add(item);
        }
        return items;
    }
}
