package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.user.UserDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.AddressMapper;
import com.solvd.dp.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    @PutMapping
    public void save(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.save(user);
    }

    @PostMapping
    public UserDto create(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User userToBeCreated = userMapper.toEntity(userDto);
        User user = userService.create(userToBeCreated);
        return userMapper.toDto(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PostMapping("/{id}/addresses")
    public void addAddress(@PathVariable Long id,
                           @Validated(OnCreate.class) @RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        userService.addAddress(id, address);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public void deleteAddress(@PathVariable Long id,
                              @PathVariable Long addressId) {
        userService.deleteAddressById(id, addressId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

}
