package com.example.dp.web.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderDto {

    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be greater than {value}")
    private Long userId;

    @NotNull(message = "Address id is required")
    @Min(value = 1, message = "Address id must be greater than {value}")
    private Long addressId;

    @NotNull(message = "Cart id is required")
    @Min(value = 1, message = "Cart id must be greater than {value}")
    private Long cartId;
}
