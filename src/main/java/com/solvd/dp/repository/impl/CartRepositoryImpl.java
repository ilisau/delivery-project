package com.solvd.dp.repository.impl;

import com.solvd.dp.config.DataSourceConfig;
import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.repository.CartRepository;
import com.solvd.dp.repository.impl.mappers.CartRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final DataSourceConfig dataSourceConfig;

    private static final String FIND_BY_ID = """
            SELECT c.id          as cart_id,
                   i.id          as item_id,
                   i.name        as item_name,
                   i.description as item_description,
                   i.price       as item_price,
                   i.type        as item_type,
                   i.available   as item_available,
                   ci.quantity   as item_quantity
            FROM carts c
                     JOIN carts_items ci ON c.id = ci.cart_id
                     LEFT JOIN items i on i.id = ci.item_id
            WHERE c.id = ?""";
    private static final String FIND_BY_USER_ID = """
            SELECT c.id          as cart_id,
                   i.id          as item_id,
                   i.name        as item_name,
                   i.description as item_description,
                   i.price       as item_price,
                   i.type        as item_type,
                   i.available   as item_available,
                   ci.quantity   as item_quantity
            FROM users u
                     JOIN carts c ON u.cart_id = c.id
                     LEFT JOIN carts_items ci ON c.id = ci.cart_id
                     LEFT JOIN items i on i.id = ci.item_id
            WHERE u.id = ?""";
    private static final String SET_CART = "UPDATE users SET cart_id = ? WHERE id = ?";
    private static final String ADD_ITEM_BY_ID = "INSERT INTO carts_items AS ci (cart_id, item_id, quantity) VALUES(?, ?, ?) ON CONFLICT (cart_id, item_id) DO UPDATE SET quantity = ci.quantity + ?";
    private static final String DELETE_ITEM_BY_ID = """
            UPDATE carts_items SET quantity = quantity - ? WHERE cart_id = ? AND item_id = ?;
            DELETE FROM carts_items WHERE cart_id = ? AND item_id = ? AND quantity <= 0""";
    private static final String CREATE = "INSERT INTO carts VALUES (default);";
    private static final String CLEAR = "DELETE FROM carts_items WHERE cart_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM carts WHERE id = ?";

    @Override
    public Optional<Cart> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(CartRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting cart by id :: " + id);
        }
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(CartRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting cart by user id :: " + userId);
        }
    }

    @Override
    public void save(Cart cart) {
        //now has no sense without editable fields
    }

    @Override
    public void create(Cart cart) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                cart.setId(key.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating cart :: " + cart);
        }
    }

    @Override
    public void clear(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CLEAR);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while clearing cart by id :: " + id);
        }
    }

    @Override
    public void setByUserId(Long cartId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SET_CART);
            statement.setLong(1, cartId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while setting cart by user id :: " + cartId);
        }
    }

    @Override
    public void addItemById(Long cartId, Long itemId, long quantity) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ITEM_BY_ID);
            statement.setLong(1, cartId);
            statement.setLong(2, itemId);
            statement.setLong(3, quantity);
            statement.setLong(4, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding item to cart by id:: " + cartId + " item id ::" + itemId + " quantity :: " + quantity);
        }
    }

    @Override
    public void deleteItemById(Long cartId, Long itemId, long quantity) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID);
            statement.setLong(1, quantity);
            statement.setLong(2, cartId);
            statement.setLong(3, itemId);
            statement.setLong(4, cartId);
            statement.setLong(5, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting item from cart by id:: " + cartId + " item id ::" + itemId + " quantity :: " + quantity);
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
            throw new ResourceMappingException("Exception while deleting cart by id :: " + id);
        }
    }

}
