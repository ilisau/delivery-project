package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.restaurant.Restaurant;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class RestaurantRowMapper implements RowMapper<Restaurant> {

    @SneakyThrows
    public static Restaurant mapRow(ResultSet resultSet) {
        resultSet.next();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(resultSet.getLong("id"));
        restaurant.setName(resultSet.getString("name"));
        restaurant.setDescription(resultSet.getString("description"));
        return restaurant;
    }

    @SneakyThrows
    public static List<Restaurant> mapRows(ResultSet resultSet) {
        List<Restaurant> restaurants = new ArrayList<>();
        while (resultSet.next()) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(resultSet.getLong("id"));
            restaurant.setName(resultSet.getString("name"));
            restaurant.setDescription(resultSet.getString("description"));
            restaurants.add(restaurant);
        }
        return restaurants;
    }
}
