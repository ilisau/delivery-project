package com.solvd.dp.repository.mappers;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.*;

public abstract class ItemRowMapper implements RowMapper<Item> {

    @SneakyThrows
    public static Item mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getLong("item_id"));
            if (!resultSet.wasNull()) {
                item.setName(resultSet.getString("item_name"));
                item.setDescription(resultSet.getString("item_description"));
                item.setPrice(resultSet.getBigDecimal("item_price"));
                item.setType(ItemType.valueOf(resultSet.getString("item_type")));
                item.setAvailable(resultSet.getBoolean("item_available"));
                return item;
            }
        }
        return null;
    }

    @SneakyThrows
    public static List<Item> mapRows(ResultSet resultSet) {
        Set<Item> items = new HashSet<>();
        while (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getLong("item_id"));
            if (!resultSet.wasNull()) {
                item.setName(resultSet.getString("item_name"));
                item.setDescription(resultSet.getString("item_description"));
                item.setPrice(resultSet.getBigDecimal("item_price"));
                item.setType(ItemType.valueOf(resultSet.getString("item_type")));
                item.setAvailable(resultSet.getBoolean("item_available"));
                items.add(item);
            }
        }
        return items.stream().toList();
    }

    @SneakyThrows
    public static Map<Item, Long> mapRowsToMap(ResultSet resultSet) {
        Map<Item, Long> items = new HashMap<>();
        while (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getLong("item_id"));
            if (!resultSet.wasNull()) {
                item.setName(resultSet.getString("item_name"));
                item.setDescription(resultSet.getString("item_description"));
                item.setPrice(resultSet.getBigDecimal("item_price"));
                item.setType(ItemType.valueOf(resultSet.getString("item_type")));
                item.setAvailable(resultSet.getBoolean("item_available"));
                items.put(item, resultSet.getLong("item_quantity"));
            }
        }
        return items;
    }

}
