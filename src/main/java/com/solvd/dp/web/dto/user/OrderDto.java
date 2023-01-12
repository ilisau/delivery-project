package com.solvd.dp.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.courier.CourierDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Address id is required", groups = {OnCreate.class, OnUpdate.class})
    private AddressDto address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CartDto cart;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CourierDto courier;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deliveredAt;

}
