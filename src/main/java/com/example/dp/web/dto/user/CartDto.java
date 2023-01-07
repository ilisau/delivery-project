package com.example.dp.web.dto.user;

import com.example.dp.web.dto.restaurant.ItemDto;
import lombok.Data;

import java.util.Map;

@Data
public class CartDto {

    private Long id;
    private Map<ItemDto, Long> items;
}
