package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.web.dto.user.AddressDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address entity);

    List<AddressDto> toDto(List<Address> entityList);

    Address toEntity(AddressDto dto);

}
