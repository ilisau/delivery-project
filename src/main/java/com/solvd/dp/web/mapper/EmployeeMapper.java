package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.web.dto.restaurant.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "entity", target = ".")
    EmployeeDto toDto(Employee entity);

    @Mapping(source = "dto", target = ".")
    Employee toEntity(EmployeeDto dto);

}
