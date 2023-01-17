package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.restaurant.CartItem;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.web.dto.restaurant.ItemDto;
import com.solvd.dp.web.dto.user.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartMapper {

    ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "cartItems", target = "items", qualifiedByName = {"toItems"})
    CartDto toDto(Cart entity);

    @Named("toItems")
    default Map<ItemDto, Long> toItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .collect(Collectors.toMap(CartItem::getItem, CartItem::getQuantity))
                .entrySet().stream().map(entry -> {
                    ItemDto itemDto = itemMapper.toDto(entry.getKey());
                    return Map.entry(itemDto, entry.getValue());
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Cart toEntity(CartDto dto);

}
