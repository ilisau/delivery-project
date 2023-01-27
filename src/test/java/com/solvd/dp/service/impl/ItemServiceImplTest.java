package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.repository.ItemRepository;
import com.solvd.dp.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Item item = new Item();
        item.setId(id);

        when(itemRepository.findById(id))
                .thenReturn(Optional.of(item));

        assertEquals(item, itemService.getById(id));
        verify(itemRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(itemRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemService.getById(id));
        verify(itemRepository).findById(id);
    }

    @Test
    void getAllByCartId() {
        Long id = 1L;
        itemService.getAllByCartId(id);

        verify(itemRepository).getAllByCartId(id);
    }

    @Test
    void getAllByType() {
        ItemType itemType = ItemType.BURGER;
        itemService.getAllByType(itemType);

        verify(itemRepository).getAllByType(itemType);
    }

    @Test
    void getAllByRestaurantId() {
        Long id = 1L;
        itemService.getAllByRestaurantId(id);

        verify(itemRepository).getAllByRestaurantId(id);
    }

    @Test
    void getAllByRestaurantIdAndType() {
        Long id = 1L;
        ItemType type = ItemType.DRINK;
        itemService.getAllByRestaurantIdAndType(id, type);

        verify(itemRepository).getAllByRestaurantIdAndType(id, type);
    }

    @Test
    void update() {
        String name = "Burger";
        String description = "Burger with cheese";
        Item item = new Item();
        item.setId(1L);
        item.setName(name);
        item.setDescription(description);

        doAnswer(invocation -> {
            Item item1 = invocation.getArgument(0);
            item1.setName(name);
            item1.setDescription(description);
            return item1;
        }).when(itemRepository).update(item);

        itemService.update(item);

        assertEquals(name, item.getName());
        assertEquals(description, item.getDescription());
        verify(itemRepository).update(item);
    }

    @Test
    void create() {
        Item item = new Item();

        doAnswer(invocation -> {
            Item item1 = invocation.getArgument(0);
            item1.setId(1L);
            return item1;
        }).when(itemRepository).create(item);

        itemService.create(item, 1L);

        assertEquals(1L, item.getId());
        verify(itemRepository).create(item);
        verify(restaurantService).addItemById(1L, item.getId());

    }

    @Test
    void getRestaurantIdByItemId() {
        Long id = 1L;

        itemService.getRestaurantIdByItemId(id);
        verify(itemRepository).getRestaurantIdByItemId(id);
    }

    @Test
    void delete() {
        Long id = 1L;
        itemService.delete(id);
        verify(itemRepository).delete(id);
    }
}