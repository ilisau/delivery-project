package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.courier.Courier;
import com.example.dp.domain.courier.CourierStatus;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class CourierRowMapper implements RowMapper<Courier> {

    @SneakyThrows
    public static Courier mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Courier courier = new Courier();
            courier.setId(resultSet.getLong("id"));
            courier.setFirstName(resultSet.getString("first_name"));
            courier.setLastName(resultSet.getString("last_name"));
            courier.setPhoneNumber(resultSet.getString("phone_number"));
            courier.setStatus(CourierStatus.valueOf(resultSet.getString("status")));
            courier.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            courier.setLastActiveAt(resultSet.getTimestamp("last_active_at").toLocalDateTime());
            return courier;
        } else {
            return null;
        }
    }

    @SneakyThrows
    public static List<Courier> mapRows(ResultSet resultSet) {
        List<Courier> couriers = new ArrayList<>();
        while(resultSet.next()) {
            Courier courier = new Courier();
            courier.setId(resultSet.getLong("id"));
            courier.setFirstName(resultSet.getString("first_name"));
            courier.setLastName(resultSet.getString("last_name"));
            courier.setPhoneNumber(resultSet.getString("phone_number"));
            courier.setStatus(CourierStatus.valueOf(resultSet.getString("status")));
            courier.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            courier.setLastActiveAt(resultSet.getTimestamp("last_active_at").toLocalDateTime());
            couriers.add(courier);
        }
        return couriers;
    }
}
