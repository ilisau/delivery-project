package com.solvd.dp.repository.impl;

import com.solvd.dp.config.DataSourceConfig;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.repository.ItemRepository;
import com.solvd.dp.repository.impl.mappers.ItemRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private static final String FIND_ALL_BY_CART_ID = """
            SELECT i.id          as item_id,
                   i.name        as item_name,
                   i.description as item_description,
                   i.price       as item_price,
                   i.type        as item_type,
                   i.available   as item_available,
                   quantity      as item_quantity
            FROM carts_items
                     LEFT JOIN items i ON carts_items.item_id = i.id
            WHERE carts_items.cart_id = ?""";
    private static final String FIND_BY_ID = """
            SELECT id          as item_id,
                   name        as item_name,
                   description as item_description,
                   price       as item_price,
                   type        as item_type,
                   available   as item_available
            FROM items
            WHERE id = ?""";
    private static final String FIND_ALL_BY_RESTAURANT_ID = """
            SELECT i.id          as item_id,
                   i.name        as item_name,
                   i.description as item_description,
                   i.price       as item_price,
                   i.type        as item_type,
                   i.available   as item_available
            FROM restaurants_items
                     LEFT JOIN items i ON restaurants_items.item_id = i.id
            WHERE restaurants_items.restaurant_id = ?""";
    private static final String FIND_ALL_BY_TYPE = """
            SELECT id          as item_id,
                   name        as item_name,
                   description as item_description,
                   price       as item_price,
                   type        as item_type,
                   available   as item_available
            FROM items
            WHERE type = ?""";
    private static final String FIND_ALL_BY_RESTAURANT_ID_AND_TYPE = """
            SELECT i.id          as item_id,
                   i.name        as item_name,
                   i.description as item_description,
                   i.price       as item_price,
                   i.type        as item_type,
                   i.available   as item_available
            FROM restaurants_items
                     LEFT JOIN items i ON restaurants_items.item_id = i.id
            WHERE restaurants_items.restaurant_id = ?
            AND type = ?""";
    private final DataSourceConfig dataSourceConfig;
    private static final String SAVE_BY_ID = "UPDATE items SET name = ?, description = ?, price = ?, type = ?, available = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO items (name, description, price, type, available) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM items WHERE id = ?";

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(ItemRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting item by id :: " + id);
        }
    }

    @Override
    public Map<Item, Long> getAllByCartId(Long cartId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_CART_ID);
            statement.setLong(1, cartId);
            try (ResultSet rs = statement.executeQuery()) {
                return ItemRowMapper.mapRowsToMap(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by cart id :: " + cartId);
        }
    }

    @Override
    public List<Item> getAllByType(ItemType type) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_TYPE);
            statement.setString(1, type.name());
            try (ResultSet rs = statement.executeQuery()) {
                return ItemRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by type :: " + type);
        }
    }

    @Override
    public List<Item> getAllByRestaurantId(Long restaurantId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID);
            statement.setLong(1, restaurantId);
            try (ResultSet rs = statement.executeQuery()) {
                return ItemRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by restaurant id :: " + restaurantId);
        }
    }

    @Override
    public List<Item> getAllByRestaurantIdAndType(Long restaurantId, ItemType type) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID_AND_TYPE);
            statement.setLong(1, restaurantId);
            statement.setString(2, type.name());
            try (ResultSet rs = statement.executeQuery()) {
                return ItemRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by restaurant id and type :: " + restaurantId + " :: " + type);
        }
    }

    @Override
    public Item save(Item itemDto) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setString(1, itemDto.getName());
            statement.setString(2, itemDto.getDescription());
            statement.setBigDecimal(3, itemDto.getPrice());
            statement.setString(4, itemDto.getType().name());
            statement.setBoolean(5, itemDto.getAvailable());
            statement.setLong(6, itemDto.getId());
            statement.executeUpdate();
            return itemDto;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving item :: " + itemDto);
        }
    }

    @Override
    public Item create(Item item) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setBigDecimal(3, item.getPrice());
            statement.setString(4, item.getType().name());
            statement.setBoolean(5, item.getAvailable());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                item.setId(key.getLong(1));
            }
            return item;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating item :: " + item);
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
            throw new ResourceMappingException("Exception while deleting item :: " + id);
        }
    }

}
