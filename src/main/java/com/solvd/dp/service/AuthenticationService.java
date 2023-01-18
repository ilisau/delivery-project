package com.solvd.dp.service;

import com.solvd.dp.web.dto.auth.JwtResponse;

public interface AuthenticationService {

    JwtResponse login(String username, String password);

    JwtResponse refresh(String refreshToken);

}
