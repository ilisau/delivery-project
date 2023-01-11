package com.solvd.dp.service;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;

import java.util.List;
import java.util.Map;

public interface ItemService {

    Item getById(Long id);

    Map<Item, Long> getAllByCartId(Long cartId);

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurantId(Long restaurantId);

    List<Item> getAllByRestaurantIdAndType(Long restaurantId, ItemType type);

    Item save(Item item);

    Item create(Item item, Long restaurantId);

    void delete(Long id);

}
