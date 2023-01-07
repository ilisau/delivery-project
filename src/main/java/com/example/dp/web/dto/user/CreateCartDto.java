package com.example.dp.web.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCartDto {

    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be greater than {value}")
    private Long userId;
}
