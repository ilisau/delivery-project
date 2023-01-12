package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.web.dto.courier.CourierDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    CourierDto toDto(Courier entity);

    List<CourierDto> toDto(List<Courier> entityList);

    Courier toEntity(CourierDto dto);

}
