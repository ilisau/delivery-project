package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.web.dto.user.OrderDto;
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
