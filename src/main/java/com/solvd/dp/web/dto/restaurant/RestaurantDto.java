package com.solvd.dp.web.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Schema(description = "Restaurant DTO")
public class RestaurantDto {

    @Schema(description = "Restaurant ID", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Restaurant name", example = "McDonalds")
    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "Restaurant description", example = "Fast food restaurant")
    @Length(max = 255, message = "Description must be less than {value} characters", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @Schema(description = "Restaurant address")
    @NotEmpty(message = "Address is required", groups = {OnCreate.class})
    @Valid
    private List<AddressDto> addresses;

    @Schema(description = "Restaurant items")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ItemDto> items;

}
