package com.solvd.dp.service;

import com.solvd.dp.web.dto.auth.JwtRequest;
import com.solvd.dp.web.dto.auth.JwtResponse;

public interface AuthenticationService {

    JwtResponse loginUser(JwtRequest jwtRequest);

    JwtResponse refreshUserTokens(String refreshToken);

    JwtResponse loginCourier(JwtRequest jwtRequest);

    JwtResponse refreshCourierTokens(String refreshToken);

    JwtResponse loginEmployee(JwtRequest jwtRequest);

    JwtResponse refreshEmployeeTokens(String refreshToken);

}
