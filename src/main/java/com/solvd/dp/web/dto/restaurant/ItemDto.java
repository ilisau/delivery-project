package com.solvd.dp.web.dto.restaurant;

import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@Schema(description = "Item DTO")
public class ItemDto {

    @Schema(description = "Item ID", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Item name", example = "Pizza")
    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "Item description", example = "Pizza with ham and cheese")
    @Length(max = 255, message = "Description must be less than {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @Schema(description = "Item price", example = "10.99")
    @NotNull(message = "Price is required", groups = {OnCreate.class, OnUpdate.class})
    @Min(value = 0, message = "Price must be greater than or equal to {value}", groups = {OnCreate.class, OnUpdate.class})
    private BigDecimal price;

    @Schema(description = "Item type", example = "PIZZA")
    @NotNull(message = "Type is required", groups = {OnCreate.class, OnUpdate.class})
    private ItemType type;

    @Schema(description = "Item availability", example = "true")
    @NotNull(message = "Restaurant id is required", groups = {OnCreate.class, OnUpdate.class})
    private Boolean available;

}
