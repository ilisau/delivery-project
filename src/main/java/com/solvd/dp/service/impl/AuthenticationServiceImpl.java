package com.solvd.dp.service.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.security.JwtTokenProvider;
import com.solvd.dp.service.AuthenticationService;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.auth.JwtRequest;
import com.solvd.dp.web.dto.auth.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CourierService courierService;
    private final EmployeeService employeeService;

    @Override
    public JwtResponse loginUser(JwtRequest jwtRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("U:" + jwtRequest.getUsername(),
                jwtRequest.getPassword()));
        User user = userService.getByEmail(jwtRequest.getUsername());
        jwtResponse.setUsername(jwtRequest.getUsername());
        jwtResponse.setToken(jwtTokenProvider.createUserToken(user.getId(),
                jwtRequest.getUsername(),
                user.getRoles().stream()
                        .map(Enum::name).collect(Collectors.toList())));
        jwtResponse.setRefreshToken(jwtTokenProvider.createUserRefreshToken(user.getId(), jwtRequest.getUsername()));
        jwtResponse.setEntityId(user.getId());
        return jwtResponse;
    }

    @Override
    public JwtResponse refreshUserTokens(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

    @Override
    public JwtResponse loginCourier(JwtRequest jwtRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("C:" + jwtRequest.getUsername(),
                jwtRequest.getPassword()));
        Courier courier = courierService.getByEmail(jwtRequest.getUsername());
        jwtResponse.setUsername(jwtRequest.getUsername());
        jwtResponse.setToken(jwtTokenProvider.createCourierToken(courier.getId(),
                jwtRequest.getUsername(),
                courier.getRoles().stream()
                        .map(Enum::name).collect(Collectors.toList())));
        jwtResponse.setRefreshToken(jwtTokenProvider.createCourierRefreshToken(courier.getId(), jwtRequest.getUsername()));
        jwtResponse.setEntityId(courier.getId());
        return jwtResponse;
    }

    @Override
    public JwtResponse refreshCourierTokens(String refreshToken) {
        return jwtTokenProvider.refreshCourierTokens(refreshToken);
    }

    @Override
    public JwtResponse loginEmployee(JwtRequest jwtRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("E:" + jwtRequest.getUsername(),
                jwtRequest.getPassword()));
        Employee employee = employeeService.getByEmail(jwtRequest.getUsername());
        jwtResponse.setUsername(jwtRequest.getUsername());
        jwtResponse.setToken(jwtTokenProvider.createEmployeeToken(employee.getId(),
                jwtRequest.getUsername(),
                employee.getRoles().stream()
                        .map(Enum::name).collect(Collectors.toList())));
        jwtResponse.setRefreshToken(jwtTokenProvider.createCourierRefreshToken(employee.getId(), jwtRequest.getUsername()));
        jwtResponse.setEntityId(employee.getId());
        return jwtResponse;
    }

    @Override
    public JwtResponse refreshEmployeeTokens(String refreshToken) {
        return jwtTokenProvider.refreshEmployeeTokens(refreshToken);
    }
}
