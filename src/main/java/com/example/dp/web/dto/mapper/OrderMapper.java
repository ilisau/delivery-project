package com.example.dp.web.dto.mapper;

import com.example.dp.domain.user.Order;
import com.example.dp.web.dto.user.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "entity", target = ".")
    OrderDto toDto(Order entity);

    @Mapping(source = "dto", target = ".")
    Order toEntity(OrderDto dto);
}
