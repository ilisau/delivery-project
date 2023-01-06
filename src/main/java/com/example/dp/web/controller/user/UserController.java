package com.example.dp.web.controller.user;

import com.example.dp.service.UserService;
import com.example.dp.web.dto.mapper.UserMapper;
import com.example.dp.web.dto.user.CreateUserDto;
import com.example.dp.web.dto.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.dp.web.controller.user.UserURLs.BASE_URL;
import static com.example.dp.web.controller.user.UserURLs.ID_URL;

@RequestMapping(BASE_URL)
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody CreateUserDto userDto) {
        return UserMapper.INSTANCE.toDto(userService.create(userDto));
    }

    @GetMapping(ID_URL)
    public UserDto getUserById(@PathVariable Long id) {
        return UserMapper.INSTANCE.toDto(userService.getById(id));
    }

    @DeleteMapping(ID_URL)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
