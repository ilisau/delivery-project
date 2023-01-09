package com.example.dp.service;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.web.dto.restaurant.CreateItemDto;

import java.util.List;
import java.util.Map;

public interface ItemService {

    Item getById(Long id);

    Map<Item, Long> getAllByCartId(Long cartId);

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurantId(Long id);

    List<Item> getAllByRestaurantIdAndType(Long id, ItemType type);

    Item save(Item item);

    Item create(CreateItemDto createItemDto);

    void deleteById(Long id);
}
