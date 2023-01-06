package com.example.dp.web.dto.restaurant;

import com.example.dp.domain.restaurant.ItemType;
import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private ItemType type;
}
