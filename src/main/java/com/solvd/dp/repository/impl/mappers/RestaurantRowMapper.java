package com.solvd.dp.repository.impl.mappers;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RestaurantRowMapper implements RowMapper<Restaurant> {

    @SneakyThrows
    public static Restaurant mapRow(ResultSet resultSet) {
        Restaurant restaurant = new Restaurant();
        List<Address> addresses = AddressRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        List<Item> items = ItemRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        List<Employee> employees = EmployeeRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            restaurant.setId(resultSet.getLong("restaurant_id"));
            if (!resultSet.wasNull()) {
                restaurant.setName(resultSet.getString("restaurant_name"));
                restaurant.setDescription(resultSet.getString("restaurant_description"));
                restaurant.setAddresses(addresses);
                restaurant.setItems(items);
                restaurant.setEmployees(employees);
                return restaurant;
            }
        }
        return null;
    }

    @SneakyThrows
    public static List<Restaurant> mapRows(ResultSet resultSet) {
        Set<Restaurant> restaurants = new HashSet<>();
        //TODO implement
        return restaurants.stream().toList();
    }

}
