package com.solvd.dp.web.mapper;

import com.solvd.dp.domain.user.User;
import com.solvd.dp.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);

}
