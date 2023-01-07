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

    private static final String FIND_BY_ID = "SELECT * FROM carts WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT * FROM users JOIN carts ON users.cart_id = carts.id WHERE users.id = 1;";
    private static final String CREATE = "INSERT INTO carts VALUES (default)";
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while creating cart :: " + createCartDto);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while deleting cart by id :: " + id);
        }
    }
}
