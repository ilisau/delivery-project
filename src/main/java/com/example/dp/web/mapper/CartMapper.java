package com.example.dp.web.mapper;

import com.example.dp.domain.user.Cart;
import com.example.dp.web.dto.user.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "entity", target = ".")
    CartDto toDto(Cart entity);

    @Mapping(source = "dto", target = ".")
    Cart toEntity(CartDto dto);
}
