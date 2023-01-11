package com.solvd.dp.domain.user;

import com.solvd.dp.domain.restaurant.Item;
import lombok.Data;

import java.util.Map;

@Data
public class Cart {

    private Long id;
    private Map<Item, Long> items;

}
