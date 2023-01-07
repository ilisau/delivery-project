package com.example.dp.service;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.web.dto.restaurant.CreateItemDto;

import java.util.List;
import java.util.Map;

public interface ItemService {

    Item getById(Long id) throws ResourceNotFoundException;

    Map<Item, Long> getAllByCartId(Long cartId) throws ResourceNotFoundException;

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurantId(Long id);

    List<Item> getAllByRestaurantIdAndType(Long id, ItemType type);

    Item save(Item item);

    Item create(CreateItemDto createItemDto) throws ResourceNotFoundException;

    void deleteById(Long id);
}
