package com.example.dp.repository.impl;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRepositoryImpl implements ItemRepository {

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Item> getAllByType(ItemType type) {
        return null;
    }

    @Override
    public List<Item> getAllByRestaurant(Restaurant restaurant) {
        return null;
    }

    @Override
    public Item save(Item itemDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
