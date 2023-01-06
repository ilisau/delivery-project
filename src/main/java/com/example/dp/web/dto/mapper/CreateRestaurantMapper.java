package com.example.dp.web.dto.mapper;

import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.dto.restaurant.RestaurantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateRestaurantMapper {
    CreateRestaurantMapper INSTANCE = Mappers.getMapper(CreateRestaurantMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateRestaurantDto toDto(Restaurant entity);

    @Mapping(source = "dto", target = ".")
    Restaurant toEntity(CreateRestaurantDto dto);
}
