package com.solvd.dp.service.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.security.JwtTokenProvider;
import com.solvd.dp.service.AuthenticationService;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.UserService;
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

    @Override
    public JwtResponse loginUser(String username, String password) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("U:" + username, password));
        User user = userService.getByEmail(username);
        jwtResponse.setUsername(username);
        jwtResponse.setToken(jwtTokenProvider.createUserToken(user.getId(),
                username,
                user.getRoles().stream()
                        .map(Enum::name).collect(Collectors.toList())));
        jwtResponse.setRefreshToken(jwtTokenProvider.createUserRefreshToken(user.getId(), username));
        jwtResponse.setEntityId(user.getId());
        return jwtResponse;
    }

    @Override
    public JwtResponse refreshUserTokens(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

    @Override
    public JwtResponse loginCourier(String username, String password) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("C:" + username, password));
        Courier courier = courierService.getByEmail(username);
        jwtResponse.setUsername(username);
        jwtResponse.setToken(jwtTokenProvider.createCourierToken(courier.getId(),
                username,
                courier.getRoles().stream()
                        .map(Enum::name).collect(Collectors.toList())));
        jwtResponse.setRefreshToken(jwtTokenProvider.createCourierRefreshToken(courier.getId(), username));
        jwtResponse.setEntityId(courier.getId());
        return jwtResponse;
    }

    @Override
    public JwtResponse refreshCourierTokens(String refreshToken) {
        return jwtTokenProvider.refreshCourierTokens(refreshToken);
    }
}
