package com.example.dp.web.mapper;

import com.example.dp.domain.courier.Courier;
import com.example.dp.web.dto.courier.CourierDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourierMapper {
    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    @Mapping(source = "entity", target = ".")
    CourierDto toDto(Courier entity);

    @Mapping(source = "dto", target = ".")
    Courier toEntity(CourierDto dto);
}
