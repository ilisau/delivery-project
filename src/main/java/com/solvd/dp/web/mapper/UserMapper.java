package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.web.dto.user.CartDto;
import com.solvd.dp.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "cart", target = "cart", qualifiedByName = {"toItems"})
    UserDto toDto(User entity);

    @Named("toItems")
    default CartDto toItems(Cart cart) {
        return cartMapper.toDto(cart);
    }

    User toEntity(UserDto dto);

}
