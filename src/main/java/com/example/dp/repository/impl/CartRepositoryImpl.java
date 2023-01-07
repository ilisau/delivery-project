package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.repository.CartRepository;
import com.example.dp.repository.impl.mappers.CartRowMapper;
import com.example.dp.web.dto.user.CreateCartDto;
import com.example.dp.web.mapper.CreateCartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final DataSource dataSource;

    private static final String FIND_BY_ID = "SELECT * FROM carts WHERE id = ?";
    private static final String FIND_ALL_BY_USER_ID = "SELECT * FROM carts WHERE user_id = ?";
    private static final String FIND_BY_USER_ID = "SELECT * FROM carts WHERE user_id = ? ORDER BY id DESC LIMIT 1";
    private static final String CREATE = "INSERT INTO carts (user_id) VALUES (?)";
    private static final String DELETE_BY_ID = "DELETE FROM carts WHERE id = ?";

    @Override
    public Optional<Cart> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet cartResultSet = statement.executeQuery();
            return Optional.of(CartRowMapper.mapRow(cartResultSet));
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting cart by id :: " + id);
        }
    }

    @Override
    public List<Cart> getAllByUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID)) {
            statement.setLong(1, user.getId());
            ResultSet cartResultSet = statement.executeQuery();
            return CartRowMapper.mapRows(cartResultSet);
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting carts by user id :: " + user.getId());
        }
    }

    @Override
    public Cart getByUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, user.getId());
            ResultSet cartResultSet = statement.executeQuery();
            return CartRowMapper.mapRow(cartResultSet);
        } catch (Exception e) {
            throw new ResourceMappingException("Exception while getting cart by user id :: " + user.getId());
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
