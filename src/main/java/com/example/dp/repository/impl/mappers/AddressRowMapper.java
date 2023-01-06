package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.user.Address;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class AddressRowMapper implements RowMapper<Address> {

    @SneakyThrows
    public static Address mapRow(ResultSet resultSet) {
        resultSet.next();
        Address address = new Address();
        address.setId(resultSet.getLong("id"));
        address.setStreetName(resultSet.getString("street_name"));
        address.setHouseNumber(resultSet.getInt("house_number"));
        address.setFloorNumber(resultSet.getInt("floor_number"));
        address.setFlatNumber(resultSet.getInt("flat_number"));
        return address;
    }

    @SneakyThrows
    public static List<Address> mapRows(ResultSet resultSet) {
        List<Address> addresses = new ArrayList<>();
        while (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getLong("id"));
            address.setStreetName(resultSet.getString("street_name"));
            address.setHouseNumber(resultSet.getInt("house_number"));
            address.setFloorNumber(resultSet.getInt("floor_number"));
            address.setFlatNumber(resultSet.getInt("flat_number"));
            addresses.add(address);
        }
        return addresses;
    }
}
