package com.example.dp.web.mapper;

import com.example.dp.domain.restaurant.Restaurant;
import com.example.dp.web.dto.restaurant.RestaurantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    @Mapping(source = "entity", target = ".")
    RestaurantDto toDto(Restaurant entity);

    @Mapping(source = "dto", target = ".")
    Restaurant toEntity(RestaurantDto dto);
}
