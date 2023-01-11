package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(Long id);

    Map<Item, Long> getAllByCartId(Long cartId);

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurantId(Long restaurantId);

    List<Item> getAllByRestaurantIdAndType(Long restaurantId, ItemType type);

    Item save(Item item);

    Item create(Item item);

    void delete(Long id);

}
