package com.solvd.dp.domain.user;

import lombok.Data;

@Data
public class Address {

    private Long id;
    private String streetName;
    private Integer houseNumber;
    private Integer floorNumber;
    private Integer flatNumber;

}
