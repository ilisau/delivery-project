package com.example.dp.web.controller;

import com.example.dp.domain.user.User;
import com.example.dp.service.UserService;
import com.example.dp.web.dto.user.CreateUserDto;
import com.example.dp.web.dto.user.UserDto;
import com.example.dp.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@Valid @RequestBody CreateUserDto userDto) {
        User user = userService.create(userDto);
        return UserMapper.INSTANCE.toDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return UserMapper.INSTANCE.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
