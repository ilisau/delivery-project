package com.example.dp.web.dto.restaurant;

import com.example.dp.web.dto.user.AddressDto;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {

    private Long id;
    private String name;
    private String description;
    private List<AddressDto> addresses;
}
