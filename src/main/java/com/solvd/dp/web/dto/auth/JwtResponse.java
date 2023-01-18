package com.solvd.dp.web.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {

    private Long userId;
    private String username;
    private String token;
    private String refreshToken;

}
