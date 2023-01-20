package com.solvd.dp.web.controller;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.AuthenticationService;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.auth.JwtRequest;
import com.solvd.dp.web.dto.auth.JwtResponse;
import com.solvd.dp.web.dto.courier.CourierDto;
import com.solvd.dp.web.dto.user.UserDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.mapper.CourierMapper;
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
    private final CourierService courierService;

    private final UserMapper userMapper;
    private final CourierMapper courierMapper;

    @PostMapping("/user/login")
    public JwtResponse loginUser(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.loginUser(jwtRequest);
    }

    @PostMapping("/user/register")
    public UserDto registerUser(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User userToBeCreated = userMapper.toEntity(userDto);
        User user = userService.create(userToBeCreated);
        return userMapper.toDto(user);
    }

    @PostMapping("/user/refresh")
    public JwtResponse refreshUser(@RequestBody String refreshToken) {
        return authenticationService.refreshUserTokens(refreshToken);
    }

    @PostMapping("/courier/login")
    public JwtResponse loginCourier(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.loginCourier(jwtRequest);
    }

    @PostMapping("/courier/register")
    public CourierDto create(@Validated(OnCreate.class) @RequestBody CourierDto courierDto) {
        Courier courierToBeCreated = courierMapper.toEntity(courierDto);
        Courier courier = courierService.create(courierToBeCreated);
        return courierMapper.toDto(courier);
    }

    @PostMapping("/courier/refresh")
    public JwtResponse refreshCourier(@RequestBody String refreshToken) {
        return authenticationService.refreshCourierTokens(refreshToken);
    }

    @PostMapping("/employee/login")
    public JwtResponse loginEmployee(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.loginEmployee(jwtRequest);
    }

    @PostMapping("/employee/refresh")
    public JwtResponse refreshEmployee(@RequestBody String refreshToken) {
        return authenticationService.refreshEmployeeTokens(refreshToken);
    }

}
