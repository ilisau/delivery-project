package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.repository.ItemRepository;
import com.example.dp.service.ItemService;
import com.example.dp.service.RestaurantService;
import com.example.dp.web.dto.restaurant.CreateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final RestaurantService restaurantService;

    @Override
    public Item getById(Long id) throws ResourceNotFoundException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
    }

    @Override
    public Map<Item, Long> getAllByCartId(Long cartId) throws ResourceNotFoundException {
        return itemRepository.getAllByCartId(cartId);
    }

    @Override
    public List<Item> getAllByType(ItemType type) {
        return itemRepository.getAllByType(type);
    }

    @Override
    public List<Item> getAllByRestaurantId(Long id) {
        return itemRepository.getAllByRestaurantId(id);
    }

    @Override
    public List<Item> getAllByRestaurantIdAndType(Long id, ItemType type) {
        return itemRepository.getAllByRestaurantIdAndType(id, type);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item create(CreateItemDto createItemDto) throws ResourceNotFoundException {
        Item item = itemRepository.create(createItemDto);
        restaurantService.addItemById(createItemDto.getRestaurantId(), item.getId());
        return item;
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.delete(id);
    }
}
