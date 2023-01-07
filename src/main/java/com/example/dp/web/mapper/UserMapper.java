package com.example.dp.web.mapper;

import com.example.dp.domain.user.User;
import com.example.dp.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "entity", target = ".")
    UserDto toDto(User entity);

    @Mapping(source = "dto", target = ".")
    User toEntity(UserDto dto);
}
