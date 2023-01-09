package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Cart;
import com.example.dp.repository.CartRepository;
import com.example.dp.repository.impl.mappers.CartRowMapper;
import com.example.dp.web.dto.user.CreateCartDto;
import com.example.dp.web.mapper.CreateCartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT id FROM carts WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT carts.id as id FROM users JOIN carts ON users.cart_id = carts.id WHERE users.id = ?";
    private static final String CREATE = "INSERT INTO carts VALUES (default)";
    private static final String CLEAR = "DELETE FROM carts_items WHERE cart_id = ?";
    private static final String ADD_ITEM_BY_ID = "INSERT INTO carts_items AS ci (cart_id, item_id, quantity) VALUES(?, ?, ?) ON CONFLICT (cart_id, item_id) DO UPDATE SET quantity = ci.quantity + ?";
    private static final String DELETE_ITEM_BY_ID = "UPDATE carts_items SET quantity = quantity - ? WHERE cart_id = ? AND item_id = ?;" +
            "DELETE FROM carts_items WHERE cart_id = ? AND item_id = ? AND quantity <= 0;";
    private static final String DELETE_BY_ID = "DELETE FROM carts WHERE id = ?";

    @Override
    public Optional<Cart> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet cartResultSet = statement.executeQuery();
            return Optional.ofNullable(CartRowMapper.mapRow(cartResultSet));
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting cart by id :: " + id);
        }
    }

    @Override
    public Cart getByUserId(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, id);
            ResultSet cartResultSet = statement.executeQuery();
            return CartRowMapper.mapRow(cartResultSet);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting cart by user id :: " + id);
        }
    }

    @Override
    public Cart save(Cart cart) {
        //now has no sense without editable fields
        return cart;
    }

    @Override
    public Cart create(CreateCartDto createCartDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, createCartDto.getUserId());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            Cart cart = CreateCartMapper.INSTANCE.toEntity(createCartDto);
            cart.setId(key.getLong(1));
            return cart;
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating cart :: " + createCartDto);
        }
    }

    @Override
    public void clear(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CLEAR)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while clearing cart by id :: " + id);
        }
    }

    @Override
    public void addItemById(Long id, Long itemId, long quantity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ITEM_BY_ID)) {
            statement.setLong(1, quantity);
            statement.setLong(2, id);
            statement.setLong(3, itemId);
            statement.setLong(3, id);
            statement.setLong(4, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding item to cart by id:: " + id + " item id ::" + itemId + " quantity :: " + quantity);
        }
    }

    @Override
    public void deleteItemById(Long id, Long itemId, long quantity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID)) {
            statement.setLong(1, id);
            statement.setLong(2, itemId);
            statement.setLong(3, id);
            statement.setLong(4, itemId);
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting item from cart by id:: " + id + " item id ::" + itemId + " quantity :: " + quantity);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting cart by id :: " + id);
        }
    }
}
