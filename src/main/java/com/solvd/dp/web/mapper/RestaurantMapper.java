package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.web.dto.restaurant.RestaurantDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto toDto(Restaurant entity);

    List<RestaurantDto> toDto(List<Restaurant> entityList);

    Restaurant toEntity(RestaurantDto dto);

}
