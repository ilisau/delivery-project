package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.Order;
import com.solvd.dp.web.dto.user.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order entity);

    List<OrderDto> toDto(List<Order> entityList);

    Order toEntity(OrderDto dto);

}
