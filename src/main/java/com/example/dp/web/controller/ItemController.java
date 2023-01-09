package com.example.dp.web.controller;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.domain.restaurant.ItemType;
import com.example.dp.service.ItemService;
import com.example.dp.web.dto.restaurant.CreateItemDto;
import com.example.dp.web.dto.restaurant.EmployeeDto;
import com.example.dp.web.dto.restaurant.ItemDto;
import com.example.dp.web.mapper.ItemMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/items")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestParam(name = "type") ItemType type) {
        List<Item> items = itemService.getAllByType(type);
        return items.stream()
                .map(ItemMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    public void save(@Valid @RequestBody ItemDto dto) {
        Item item = ItemMapper.INSTANCE.toEntity(dto);
        itemService.save(item);
    }

    @PostMapping
    public ItemDto create(@Valid @RequestBody CreateItemDto itemDto) {
        Item item = itemService.create(itemDto);
        return ItemMapper.INSTANCE.toDto(item);
    }

    @GetMapping("/by-restaurant/{id}")
    public List<ItemDto> getAllByRestaurantId(@PathVariable Long id,
                                              @RequestParam(name = "type", required = false) ItemType type) {
        List<Item> items;
        if(type != null) {
            items = itemService.getAllByRestaurantIdAndType(id, type);
        } else {
            items = itemService.getAllByRestaurantId(id);
        }
        return items.stream()
                .map(ItemMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id) {
        Item item = itemService.getById(id);
        return ItemMapper.INSTANCE.toDto(item);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
    }
}
