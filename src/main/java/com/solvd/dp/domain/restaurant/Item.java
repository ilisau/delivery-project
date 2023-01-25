package com.solvd.dp.domain.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ItemType type;
    private Boolean available;

}
