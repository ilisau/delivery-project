package com.example.dp.web.mapper;

import com.example.dp.domain.restaurant.Item;
import com.example.dp.web.dto.restaurant.CreateItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateItemMapper {
    CreateItemMapper INSTANCE = Mappers.getMapper(CreateItemMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateItemDto toDto(Item entity);

    @Mapping(source = "dto", target = ".")
    Item toEntity(CreateItemDto dto);
}
