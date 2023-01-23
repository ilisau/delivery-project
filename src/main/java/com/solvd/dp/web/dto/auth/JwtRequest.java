package com.solvd.dp.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for authentication")
public class JwtRequest {

    @Schema(description = "Username", example = "peter@gmail.com")
    @NotNull(message = "Username is required")
    private String username;

    @Schema(description = "Password", example = "12345")
    @NotNull(message = "Password is required")
    private String password;

}
