package com.solvd.dp.domain.restaurant;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ItemType type;
    private Boolean available;

}
