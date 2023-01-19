package com.solvd.dp.service;

import com.solvd.dp.web.dto.auth.JwtResponse;

public interface AuthenticationService {

    JwtResponse loginUser(String username, String password);

    JwtResponse refreshUserTokens(String refreshToken);

    JwtResponse loginCourier(String username, String password);

    JwtResponse refreshCourierTokens(String refreshToken);

}
