package com.example.dp.repository.impl;

import com.example.dp.domain.exception.ResourceMappingException;
import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.repository.UserRepository;
import com.example.dp.repository.impl.mappers.UserRowMapper;
import com.example.dp.web.dto.user.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String FIND_USER_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number = ?";
    private static final String SAVE_BY_ID = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";
    private static final String CREATE = "INSERT INTO users (name, email, phone_number, password, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_ADDRESS = "INSERT INTO users_addresses (user_id, address_id) VALUES (?, ?)";
    private static final String DELETE_ADDRESS = "DELETE FROM users_addresses WHERE user_id = ? AND address_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    @Override
    public Optional<User> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setLong(1, id);
            return Optional.of(UserRowMapper.mapRow(statement.executeQuery()));
        } catch (SQLException e) {
            throw new ResourceMappingException("User not found for this id :: " + id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            statement.setString(1, email);
            return Optional.of(UserRowMapper.mapRow(statement.executeQuery()));
        } catch (SQLException e) {
            throw new ResourceMappingException("User not found for this email :: " + email);
        }
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            return Optional.of(UserRowMapper.mapRow(statement.executeQuery()));
        } catch (SQLException e) {
            throw new ResourceMappingException("User not found for this phone number :: " + phoneNumber);
        }
    }

    @Override
    public User save(User user) {
//        try (Connection connection = dataSource.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getLastName());
//            statement.setString(3, user.getEmail());
//            statement.setString(4, user.getPhoneNumber());
//            statement.setString(5, user.getPassword());
//            statement.setString(6, user.getRole().toString());
//            statement.setLong(7, user.getId());
//            statement.executeUpdate();
//            return user;
//        } catch (SQLException e) {
//            throw new ResourceMappingException("User not found for this id :: " + user.getId());
//        }
        return null;
    }

    @Override
    public User create(CreateUserDto userDto) {
        return null;
    }

    @Override
    public void addAddress(Long userId, Long addressId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ADDRESS)) {
            statement.setLong(1, userId);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding address to user with id :: " + userId);
        }
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS)) {
            statement.setLong(1, userId);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting address from user with id :: " + userId);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting user with id :: " + id);
        }
    }
}
