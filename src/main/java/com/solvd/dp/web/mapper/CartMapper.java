package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.web.dto.user.CartDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart entity);

    Cart toEntity(CartDto dto);

}
