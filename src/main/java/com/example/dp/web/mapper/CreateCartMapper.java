package com.example.dp.web.mapper;

import com.example.dp.domain.user.Cart;
import com.example.dp.web.dto.user.CreateCartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateCartMapper {
    CreateCartMapper INSTANCE = Mappers.getMapper(CreateCartMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateCartDto toDto(Cart entity);

    @Mapping(source = "dto", target = ".")
    Cart toEntity(CreateCartDto dto);
}
