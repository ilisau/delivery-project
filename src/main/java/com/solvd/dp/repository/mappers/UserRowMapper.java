package com.solvd.dp.repository.mappers;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UserRowMapper implements RowMapper<User> {

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        User user = new User();
        Cart cart = CartRowMapper.mapRow(resultSet);
        resultSet.beforeFirst();
        List<Address> addresses = AddressRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        Set<Role> roles = getRoles(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("user_id"));
            if (!resultSet.wasNull()) {
                user.setName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
                user.setPhoneNumber(resultSet.getString("user_phone_number"));
                user.setCreatedAt(resultSet.getTimestamp("user_created_at").toLocalDateTime());
                user.setCart(cart);
                user.setAddresses(addresses);
                user.setRoles(roles);
                return user;
            }
        }
        return null;
    }

    @SneakyThrows
    private static Set<Role> getRoles(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            Role role = Role.valueOf(resultSet.getString("role_name"));
            roles.add(role);
        }
        return roles;
    }

}
