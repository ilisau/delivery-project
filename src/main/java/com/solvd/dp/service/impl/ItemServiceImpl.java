package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.repository.ItemRepository;
import com.solvd.dp.service.ItemService;
import com.solvd.dp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final RestaurantService restaurantService;

    @Override
    @Transactional(readOnly = true)
    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Item, Long> getAllByCartId(Long cartId) {
        return itemRepository.getAllByCartId(cartId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAllByType(ItemType type) {
        return itemRepository.getAllByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAllByRestaurantId(Long restaurantId) {
        return itemRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAllByRestaurantIdAndType(Long restaurantId, ItemType type) {
        return itemRepository.getAllByRestaurantIdAndType(restaurantId, type);
    }

    @Override
    @Transactional
    public Item update(Item item) {
        itemRepository.update(item);
        return item;
    }

    @Override
    @Transactional
    public Item create(Item item, Long restaurantId) {
        itemRepository.create(item);
        restaurantService.addItemById(restaurantId, item.getId());
        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRestaurantIdByItemId(Long itemId) {
        return itemRepository.getRestaurantIdByItemId(itemId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        itemRepository.delete(id);
    }

}
