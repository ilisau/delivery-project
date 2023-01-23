package com.solvd.dp.repository.impl;

import com.solvd.dp.domain.exception.ResourceMappingException;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.repository.DataSourceConfig;
import com.solvd.dp.repository.UserRepository;
import com.solvd.dp.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

//@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private static final String FIND_USER_BY_ID = """
            SELECT u.id           as user_id,
                           u.name         as user_name,
                           u.email        as user_email,
                           u.phone_number as user_phone_number,
                           u.password     as user_password,
                           u.created_at   as user_created_at,
                           c.id           as cart_id,
                           i.id           as item_id,
                           i.name         as item_name,
                           i.description  as item_description,
                           i.type         as item_type,
                           i.price        as item_price,
                           i.available    as item_available,
                           ci.quantity    as item_quantity,
                           a.id           as address_id,
                           a.street_name  as address_street_name,
                           a.house_number as address_house_number,
                           a.floor_number as address_floor_number,
                           a.flat_number  as address_flat_number,
                           ur.role        as role_name
                    FROM users u
                             LEFT JOIN carts c on u.cart_id = c.id
                             LEFT JOIN carts_items ci on c.id = ci.cart_id
                             LEFT JOIN items i on ci.item_id = i.id
                             LEFT JOIN users_addresses ua on u.id = ua.user_id
                             LEFT JOIN addresses a on ua.address_id = a.id
                             LEFT JOIN users_roles ur on u.id = ur.user_id
                    WHERE u.id = ?""";
    private static final String FIND_USER_BY_EMAIL = """
            SELECT u.id           as user_id,
                   u.name         as user_name,
                   u.email        as user_email,
                                          u.phone_number as user_phone_number,
                                          u.password     as user_password,
                                          u.created_at   as user_created_at,
                                          c.id           as cart_id,
                                          i.id           as item_id,
                                          i.name         as item_name,
                                          i.description  as item_description,
                                          i.type         as item_type,
                                          i.price        as item_price,
                                          i.available    as item_available,
                                          ci.quantity    as item_quantity,
                                          a.id           as address_id,
                                          a.street_name  as address_street_name,
                                          a.house_number as address_house_number,
                                          a.floor_number as address_floor_number,
                                          a.flat_number  as address_flat_number,
                                          ur.role        as role_name
            FROM users u
                LEFT JOIN carts c on u.cart_id = c.id
                                            LEFT JOIN carts_items ci on c.id = ci.cart_id
                                            LEFT JOIN items i on ci.item_id = i.id
                                            LEFT JOIN users_addresses ua on u.id = ua.user_id
                                            LEFT JOIN addresses a on ua.address_id = a.id
                                            LEFT JOIN users_roles ur on u.id = ur.user_id
                                   WHERE u.email = ?""";
    private static final String FIND_USER_BY_PHONE_NUMBER = """
            SELECT u.id           as user_id,
                                                 u.name         as user_name,
                                                 u.email        as user_email,
                                                 u.phone_number as user_phone_number,
                                                 u.password     as user_password,
                                                 u.created_at   as user_created_at,
                                                 c.id           as cart_id,
                                                 i.id           as item_id,
                                                 i.name         as item_name,
                                                 i.description  as item_description,
                                                 i.type         as item_type,
                                                 i.price        as item_price,
                                                 i.available    as item_available,
                                                 ci.quantity    as item_quantity,
                                                 a.id           as address_id,
                                                 a.street_name  as address_street_name,
                                                 a.house_number as address_house_number,
                                                 a.floor_number as address_floor_number,
                                                 a.flat_number  as address_flat_number,
                                                 ur.role        as role_name
                                          FROM users u
                                                   LEFT JOIN carts c on u.cart_id = c.id
                                                   LEFT JOIN carts_items ci on c.id = ci.cart_id
                                                   LEFT JOIN items i on ci.item_id = i.id
                                                   LEFT JOIN users_addresses ua on u.id = ua.user_id
                                                   LEFT JOIN addresses a on ua.address_id = a.id
                                                   LEFT JOIN users_roles ur on u.id = ur.user_id
                                          WHERE u.phone_number = ?""";
    private static final String IS_EXISTS = "SELECT id FROM users WHERE email = ? or phone_number = ?";
    private static final String CREATE = """
            INSERT INTO users (name, email, phone_number, password, created_at, cart_id)
            VALUES (?, ?, ?, ?, ?, ?)""";
    private static final String SAVE_ROLES = "INSERT INTO users_roles (user_id, role) VALUES (?, ?)";
    private static final String SAVE_BY_ID = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";
    private static final String ADD_ADDRESS = "INSERT INTO users_addresses (user_id, address_id) VALUES (?, ?)";
    private static final String DELETE_ADDRESS = "DELETE FROM users_addresses WHERE user_id = ? AND address_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting user by id :: " + id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting user by email :: " + email);
        }
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_PHONE_NUMBER);
            statement.setString(1, phoneNumber);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while getting user by phone number :: " + phoneNumber);
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_BY_ID);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving user :: " + user.getId());
        }
    }

    @Override
    public void create(User user, Long cartId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
            statement.setLong(6, cartId);
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                key.next();
                user.setId(key.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while creating user :: " + user.getId());
        }
    }

    @Override
    public void saveRoles(Long id, Set<Role> roles) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_ROLES);
            for (Role role : roles) {
                statement.setLong(1, id);
                statement.setString(2, role.name());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while saving roles for user :: " + id);
        }
    }

    @Override
    public void addAddressById(Long userId, Long addressId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_ADDRESS);
            statement.setLong(1, userId);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while adding address to user with id :: " + userId);
        }
    }

    @Override
    public void deleteAddressById(Long userId, Long addressId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS);
            statement.setLong(1, userId);
            statement.setLong(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while deleting address from user with id :: " + userId);
        }
    }

    @Override
    public boolean exists(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_EXISTS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPhoneNumber());
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong(1);
                    if (!Objects.equals(user.getId(), id)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Exception while checking if user exists :: " + user.getId());
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
            throw new ResourceMappingException("Exception while deleting user with id :: " + id);
        }
    }

}
