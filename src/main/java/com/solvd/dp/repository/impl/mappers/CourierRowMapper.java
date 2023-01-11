package com.solvd.dp.repository.impl.mappers;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
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
            courier.setId(resultSet.getLong("courier_id"));
            if (!resultSet.wasNull()) {
                courier.setFirstName(resultSet.getString("courier_first_name"));
                courier.setLastName(resultSet.getString("courier_last_name"));
                courier.setPhoneNumber(resultSet.getString("courier_phone_number"));
                courier.setStatus(CourierStatus.valueOf(resultSet.getString("courier_status")));
                courier.setCreatedAt(resultSet.getTimestamp("courier_created_at").toLocalDateTime());
                courier.setLastActiveAt(resultSet.getTimestamp("courier_last_active_at").toLocalDateTime());
                return courier;
            }
        }
        return null;
    }

    @SneakyThrows
    public static List<Courier> mapRows(ResultSet resultSet) {
        List<Courier> couriers = new ArrayList<>();
        while(resultSet.next()) {
            Courier courier = new Courier();
            courier.setId(resultSet.getLong("courier_id"));
            if (!resultSet.wasNull()) {
                courier.setFirstName(resultSet.getString("courier_first_name"));
                courier.setLastName(resultSet.getString("courier_last_name"));
                courier.setPhoneNumber(resultSet.getString("courier_phone_number"));
                courier.setStatus(CourierStatus.valueOf(resultSet.getString("courier_status")));
                courier.setCreatedAt(resultSet.getTimestamp("courier_created_at").toLocalDateTime());
                courier.setLastActiveAt(resultSet.getTimestamp("courier_last_active_at").toLocalDateTime());
                couriers.add(courier);
            }
        }
        return couriers;
    }

}
