package com.solvd.dp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private Long id;
    private String streetName;
    private Integer houseNumber;
    private Integer floorNumber;
    private Integer flatNumber;

}
