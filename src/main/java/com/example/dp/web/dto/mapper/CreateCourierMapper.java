package com.example.dp.web.dto.mapper;

import com.example.dp.domain.courier.Courier;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.dto.courier.CreateCourierDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateCourierMapper {
    CreateCourierMapper INSTANCE = Mappers.getMapper(CreateCourierMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateCourierDto toDto(Courier entity);

    @Mapping(source = "dto", target = ".")
    Courier toEntity(CreateCourierDto dto);
}
