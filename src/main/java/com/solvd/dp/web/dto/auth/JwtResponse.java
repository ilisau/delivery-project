package com.solvd.dp.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Response with JWT token")
public class JwtResponse {

    @Schema(description = "Id of entity - courier, user or employee", example = "1")
    private Long entityId;

    @Schema(description = "Email", example = "mike@example.com")
    private String username;

    @Schema(description = "Access token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlckBnbWFpbC5jb20iLCJ0eXBlIjoiVSIsImlkIjoxLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjc0NDc1MDE4LCJleHAiOjE2NzQ0Nzg2MTh9.gj63W-C_E87cu70BcS6Upq7Yj2K7GEcmjWkyef4kGx4")
    private String token;

    @Schema(description = "Refresh token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlckBnbWFpbC5jb20iLCJpZCI6MSwiaWF0IjoxNjc0NDc1MDE4LCJleHAiOjE2NzcwNjcwMTh9.4Wot7ZGAiQUcM_Wf1oZyQU8BE_7GTAn2tpS_KVWZRqw")
    private String refreshToken;

}
