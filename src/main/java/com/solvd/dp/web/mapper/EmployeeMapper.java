package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.web.dto.restaurant.EmployeeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(Employee entity);

    List<EmployeeDto> toDto(List<Employee> entityList);

    Employee toEntity(EmployeeDto dto);

}
