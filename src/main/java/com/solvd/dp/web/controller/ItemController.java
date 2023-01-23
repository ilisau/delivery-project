package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.service.ItemService;
import com.solvd.dp.web.dto.restaurant.ItemDto;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/items")
@RestController
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @GetMapping
    public List<ItemDto> getAll(@RequestParam ItemType type) {
        List<Item> items = itemService.getAllByType(type);
        return itemMapper.toDto(items);
    }

    @PutMapping
    @PreAuthorize("canAccessItem(#dto.id)")
    public void update(@Validated(OnUpdate.class) @RequestBody ItemDto dto) {
        Item item = itemMapper.toEntity(dto);
        itemService.update(item);
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable Long id) {
        Item item = itemService.getById(id);
        return itemMapper.toDto(item);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("canAccessItem(#id)")
    public void deleteById(@PathVariable Long id) {
        itemService.delete(id);
    }

}
