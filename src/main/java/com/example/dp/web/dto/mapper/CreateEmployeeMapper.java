package com.example.dp.web.dto.mapper;

import com.example.dp.domain.restaurant.Employee;
import com.example.dp.web.dto.restaurant.CreateEmployeeDto;
import com.example.dp.web.dto.restaurant.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateEmployeeMapper {
    CreateEmployeeMapper INSTANCE = Mappers.getMapper(CreateEmployeeMapper.class);

    @Mapping(source = "entity", target = ".")
    CreateEmployeeDto toDto(Employee entity);

    @Mapping(source = "dto", target = ".")
    Employee toEntity(CreateEmployeeDto dto);
}
