package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.AuthenticationService;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.auth.JwtRequest;
import com.solvd.dp.web.dto.auth.JwtResponse;
import com.solvd.dp.web.dto.user.UserDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/user/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.login(jwtRequest.getUsername(), jwtRequest.getPassword());
    }

    @PostMapping("/user/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User userToBeCreated = userMapper.toEntity(userDto);
        User user = userService.create(userToBeCreated);
        return userMapper.toDto(user);
    }

    @PostMapping("/user/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authenticationService.refresh(refreshToken);
    }

}
