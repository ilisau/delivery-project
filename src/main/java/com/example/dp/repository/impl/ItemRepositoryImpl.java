package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.repository.ItemRepository;
import com.example.dp.repository.impl.mappers.ItemRowMapper;
import com.example.dp.web.dto.restaurant.CreateItemDto;
import com.example.dp.web.mapper.CreateItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT id, name, description, price, type, available FROM items WHERE id = ?";
    private static final String FIND_ALL_BY_CART_ID = "SELECT items.id as id, items.name as name, items.description as description, items.price as price, items.type as type, items.available as available, quantity FROM carts_items JOIN items ON carts_items.item_id = items.id WHERE carts_items.cart_id = ?";
    private static final String FIND_ALL_BY_TYPE = "SELECT id, name, description, price, type, available FROM items WHERE type = ?";
    private static final String FIND_ALL_BY_RESTAURANT_ID = "SELECT items.id as id, items.name as name, items.description as description, items.price as price, items.type as type, items.available as available FROM restaurants_items JOIN items ON restaurants_items.item_id = items.id WHERE restaurants_items.restaurant_id = ?";
    private static final String FIND_ALL_BY_RESTAURANT_ID_AND_TYPE = "SELECT items.id as id, items.name as name, items.description as description, items.price as price, items.type as type, items.available as available FROM restaurants_items JOIN items ON restaurants_items.item_id = items.id WHERE restaurants_items.restaurant_id = ? AND items.type = ?";
    private static final String SAVE_BY_ID = "UPDATE items SET name = ?, description = ?, price = ?, type = ?, available = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO items (name, description, price, type, available) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM items WHERE id = ?";

    @Override
    public Optional<Item> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet itemResultSet = statement.executeQuery();
            return Optional.ofNullable(ItemRowMapper.mapRow(itemResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting item by id :: " + id);
        }
    }

    @Override
    public Map<Item, Long> getAllByCartId(Long cartId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_CART_ID)) {
            statement.setLong(1, cartId);
            ResultSet itemResultSet = statement.executeQuery();
            return ItemRowMapper.mapRowsToMap(itemResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by cart id :: " + cartId);
        }
    }

    @Override
    public List<Item> getAllByType(ItemType type) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_TYPE)) {
            statement.setString(1, type.name());
            ResultSet itemResultSet = statement.executeQuery();
            return ItemRowMapper.mapRows(itemResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by type :: " + type);
        }
    }

    @Override
    public List<Item> getAllByRestaurantId(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID)) {
            statement.setLong(1, id);
            ResultSet itemResultSet = statement.executeQuery();
            return ItemRowMapper.mapRows(itemResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by restaurant id :: " + id);
        }
    }

    @Override
    public List<Item> getAllByRestaurantIdAndType(Long id, ItemType type) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_RESTAURANT_ID_AND_TYPE)) {
            statement.setLong(1, id);
            statement.setString(2, type.name());
            ResultSet itemResultSet = statement.executeQuery();
            return ItemRowMapper.mapRows(itemResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting items by restaurant id and type :: " + id + " :: " + type);
        }
    }

    @Override
    public Item save(Item itemDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID)) {
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
    public Item create(CreateItemDto itemDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setString(1, itemDto.getName());
            statement.setString(2, itemDto.getDescription());
            statement.setBigDecimal(3, itemDto.getPrice());
            statement.setString(4, itemDto.getType().name());
            statement.setBoolean(5, true);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            Item item = CreateItemMapper.INSTANCE.toEntity(itemDto);
            item.setAvailable(true);
            item.setId(key.getLong(1));
            return item;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating item :: " + itemDto);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting item :: " + id);
        }
    }
}
