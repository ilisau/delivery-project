package com.example.dp.repository;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.domain.restaurant.Restaurant;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(Long id);

    List<Item> getAllByType(ItemType type);

    List<Item> getAllByRestaurant(Restaurant restaurant);

    Item save(Item item);

    void delete(Long id);
}
