package com.example.dp.repository;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.CreateItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(Long id);

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurantId(Long id);

    List<Item> getAllByRestaurantIdAndType(Long id, ItemType type);

    Item save(Item item);

    Item create(CreateItemDto itemDto);

    void delete(Long id);
}
