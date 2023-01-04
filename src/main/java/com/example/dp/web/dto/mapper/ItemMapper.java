package com.example.dp.web.dto.mapper;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.web.dto.restaurant.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "entity", target = ".")
    ItemDto toDto(Item entity);

    @Mapping(source = "dto", target = ".")
    Item toEntity(ItemDto dto);
}
