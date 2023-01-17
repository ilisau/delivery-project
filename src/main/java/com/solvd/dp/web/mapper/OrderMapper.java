package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.web.dto.user.CartDto;
import com.solvd.dp.web.dto.user.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "cart", target = "cart", qualifiedByName = {"toItems"})
    OrderDto toDto(Order entity);

    @Mapping(source = "cart", target = "cart", qualifiedByName = {"toItems"})
    List<OrderDto> toDto(List<Order> entityList);

    @Named("toItems")
    default CartDto toItems(Cart cart) {
        return cartMapper.toDto(cart);
    }

    Order toEntity(OrderDto dto);

}
