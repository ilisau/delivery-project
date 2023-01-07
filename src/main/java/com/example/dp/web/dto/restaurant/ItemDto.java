package com.example.dp.web.dto.restaurant;

import com.example.dp.domain.restaurant.ItemType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ItemType type;
    private Boolean available;
}
