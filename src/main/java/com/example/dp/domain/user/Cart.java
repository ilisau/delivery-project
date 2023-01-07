package com.example.dp.domain.user;

import com.example.dp.domain.restaurant.Item;
import lombok.Data;

import java.util.Map;

@Data
public class Cart {

    private Long id;
    private Map<Item, Long> items;
}
