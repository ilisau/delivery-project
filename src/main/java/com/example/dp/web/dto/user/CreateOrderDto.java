package com.example.dp.web.dto.user;

import com.example.dp.domain.user.OrderStatus;
import com.example.dp.web.dto.courier.CourierDto;
import com.example.dp.web.dto.restaurant.RestaurantDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class CreateOrderDto {

    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be greater than {value}")
    private Long userId;

    @NotNull(message = "Address id is required")
    @Min(value = 1, message = "Address id must be greater than {value}")
    private Long addressId;

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than {value}")
    private Long restaurantId;

    @NotNull(message = "Cart id is required")
    @Min(value = 1, message = "Cart id must be greater than {value}")
    private Long cartId;
}
