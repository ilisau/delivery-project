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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final CourierService courierService;

    private final UserMapper userMapper;
    private final CourierMapper courierMapper;

    @PostMapping("/user/login")
    @Operation(summary = "Login user")
    public JwtResponse loginUser(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.loginUser(jwtRequest);
    }

    @PostMapping("/user/register")
    @Operation(summary = "Register user")
    public UserDto registerUser(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User userToBeCreated = userMapper.toEntity(userDto);
        User user = userService.create(userToBeCreated);
        return userMapper.toDto(user);
    }

    @PostMapping("/user/refresh")
    @Operation(summary = "Refresh user token")
    public JwtResponse refreshUser(@RequestBody String refreshToken) {
        return authenticationService.refreshUserTokens(refreshToken);
    }

    @PostMapping("/courier/login")
    @Operation(summary = "Login courier")
    public JwtResponse loginCourier(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.loginCourier(jwtRequest);
    }

    @PostMapping("/courier/register")
    @Operation(summary = "Register courier")
    public CourierDto create(@Validated(OnCreate.class) @RequestBody CourierDto courierDto) {
        Courier courierToBeCreated = courierMapper.toEntity(courierDto);
        Courier courier = courierService.create(courierToBeCreated);
        return courierMapper.toDto(courier);
    }

    @PostMapping("/courier/refresh")
    @Operation(summary = "Refresh courier token")
    public JwtResponse refreshCourier(@RequestBody String refreshToken) {
        return authenticationService.refreshCourierTokens(refreshToken);
    }

    @PostMapping("/employee/login")
    @Operation(summary = "Login employee")
    public JwtResponse loginEmployee(@Validated @RequestBody JwtRequest jwtRequest) {
        return authenticationService.loginEmployee(jwtRequest);
    }

    @PostMapping("/employee/refresh")
    @Operation(summary = "Refresh employee token")
    public JwtResponse refreshEmployee(@RequestBody String refreshToken) {
        return authenticationService.refreshEmployeeTokens(refreshToken);
    }

}
