package com.example.dp.web.dto.restaurant;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {

    private Long id;
    private String name;
    private String description;
    private List<ItemDto> items;
}
