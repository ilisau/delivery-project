package com.example.dp.web.dto.user;

import com.example.dp.web.dto.restaurant.ItemDto;
import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private Long id;
    private List<ItemDto> items;
    private UserDto user;
}
