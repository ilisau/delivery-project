package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.service.ItemService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.restaurant.ItemDto;
import com.solvd.dp.web.mapper.ItemMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/items")
@RestController
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestParam ItemType type) {
        List<Item> items = itemService.getAllByType(type);
        return items.stream()
                .map(ItemMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody ItemDto dto) {
        Item item = ItemMapper.INSTANCE.toEntity(dto);
        itemService.save(item);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public List<ItemDto> getAllByRestaurantId(@PathVariable Long restaurantId,
                                              @RequestParam(required = false) ItemType type) {
        List<Item> items;
        if (type == null) {
            items = itemService.getAllByRestaurantId(restaurantId);
        } else {
            items = itemService.getAllByRestaurantIdAndType(restaurantId, type);
        }
        return items.stream()
                .map(ItemMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/restaurants/{restaurantId}")
    @Validated(OnCreate.class)
    public ItemDto create(@PathVariable Long restaurantId,
                          @Valid @RequestBody ItemDto itemDto) {
        Item itemToBeCreated = ItemMapper.INSTANCE.toEntity(itemDto);
        Item item = itemService.create(itemToBeCreated, restaurantId);
        return ItemMapper.INSTANCE.toDto(item);
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id) {
        Item item = itemService.getById(id);
        return ItemMapper.INSTANCE.toDto(item);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemService.delete(id);
    }

}
