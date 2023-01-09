package com.example.dp.repository.impl.mappers;

import com.example.dp.domain.user.Address;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AddressRowMapper implements RowMapper<Address> {

    @SneakyThrows
    public static Address mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getLong("address_id"));
            if (!resultSet.wasNull()) {
                address.setStreetName(resultSet.getString("address_street_name"));
                address.setHouseNumber(resultSet.getInt("address_house_number"));
                address.setFloorNumber(resultSet.getInt("address_floor_number"));
                address.setFlatNumber(resultSet.getInt("address_flat_number"));
                return address;
            }
        }
        return null;
    }

    @SneakyThrows
    public static List<Address> mapRows(ResultSet resultSet) {
        Set<Address> addressSet = new LinkedHashSet<>();
        while (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getLong("address_id"));
            if (!resultSet.wasNull()) {
                address.setStreetName(resultSet.getString("address_street_name"));
                address.setHouseNumber(resultSet.getInt("address_house_number"));
                address.setFloorNumber(resultSet.getInt("address_floor_number"));
                address.setFlatNumber(resultSet.getInt("address_flat_number"));
                addressSet.add(address);
            }
        }
        return addressSet.stream().toList();
    }
}
