package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.user.User;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class UserRowMapper implements RowMapper<User> {

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            return user;
        } else {
            return null;
        }
    }

    @SneakyThrows
    public static List<User> mapRows(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            users.add(user);
        }
        return users;
    }
}
