package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.user.UserDto;
import com.solvd.dp.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        userService.save(user);
    }

    @PostMapping
    @Validated(OnCreate.class)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        User userToBeCreated = UserMapper.INSTANCE.toEntity(userDto);
        User user = userService.create(userToBeCreated);
        return UserMapper.INSTANCE.toDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return UserMapper.INSTANCE.toDto(user);
    }

    @PostMapping("/{id}/addresses/{addressId}")
    public void addAddress(@PathVariable Long id, @PathVariable Long addressId) {
        userService.addAddressById(id, addressId);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public void deleteAddress(@PathVariable Long id, @PathVariable Long addressId) {
        userService.deleteAddressById(id, addressId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

}
