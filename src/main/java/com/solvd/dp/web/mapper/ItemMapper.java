package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.web.dto.restaurant.ItemDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto toDto(Item entity);

    List<ItemDto> toDto(List<Item> entityList);

    Item toEntity(ItemDto dto);

}
