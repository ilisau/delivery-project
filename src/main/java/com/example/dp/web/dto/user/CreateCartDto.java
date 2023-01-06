package com.example.dp.web.dto.user;

import com.example.dp.web.dto.restaurant.ItemDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateCartDto {

    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be greater than {value}")
    private Long userId;
}
