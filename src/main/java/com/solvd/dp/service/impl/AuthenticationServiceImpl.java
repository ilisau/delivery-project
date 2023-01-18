package com.solvd.dp.service.impl;

import com.solvd.dp.domain.user.User;
import com.solvd.dp.security.JwtTokenProvider;
import com.solvd.dp.service.AuthenticationService;
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

    @Override
    public JwtResponse login(String username, String password) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userService.getByEmail(username);
        jwtResponse.setUsername(username);
        jwtResponse.setToken(jwtTokenProvider.createToken(user.getId(),
                username,
                user.getRoles().stream()
                        .map(Enum::name).collect(Collectors.toList())));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), username));
        jwtResponse.setUserId(user.getId());
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshTokens(refreshToken);
    }
}
