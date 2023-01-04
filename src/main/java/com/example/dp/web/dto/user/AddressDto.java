package com.example.dp.web.dto.user;

import lombok.Data;

@Data
public class AddressDto {

    private Long id;
    private String streetName;
    private Integer houseNumber;
    private Integer floorNumber;
    private Integer flatNumber;
}
