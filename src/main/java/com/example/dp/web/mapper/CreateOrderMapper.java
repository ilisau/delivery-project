package com.example.dp.web.mapper;

import com.example.dp.domain.user.Order;
import com.example.dp.web.dto.user.CreateOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateOrderMapper {
    CreateOrderMapper INSTANCE = Mappers.getMapper(CreateOrderMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateOrderDto toDto(Order entity);

    @Mapping(source = "dto", target = ".")
    Order toEntity(CreateOrderDto dto);
}
