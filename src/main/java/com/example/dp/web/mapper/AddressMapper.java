package com.example.dp.web.mapper;

import com.example.dp.domain.user.Address;
import com.example.dp.web.dto.user.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "entity", target = ".")
    AddressDto toDto(Address entity);

    @Mapping(source = "dto", target = ".")
    Address toEntity(AddressDto dto);
}
