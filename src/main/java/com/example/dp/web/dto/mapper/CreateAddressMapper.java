package com.example.dp.web.dto.mapper;

import com.example.dp.domain.user.Address;
import com.example.dp.web.dto.user.CreateAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateAddressMapper {
    CreateAddressMapper INSTANCE = Mappers.getMapper(CreateAddressMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateAddressDto toDto(Address entity);

    @Mapping(source = "dto", target = ".")
    Address toEntity(CreateAddressDto dto);
}
