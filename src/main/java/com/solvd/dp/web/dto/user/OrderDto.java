package com.solvd.dp.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.web.dto.courier.CourierDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Order DTO")
public class OrderDto {

    @Schema(description = "Order ID", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Order address")
    @NotNull(message = "Address id is required", groups = {OnCreate.class, OnUpdate.class})
    private AddressDto address;

    @Schema(description = "Order cart")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CartDto cart;

    @Schema(description = "Order status", example = "ORDERED")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;

    @Schema(description = "Order courier")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CourierDto courier;

    @Schema(description = "Order creation date", example = "2021-01-01T00:00:00")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Order delivery date", example = "2021-01-01T00:00:00")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deliveredAt;

}
