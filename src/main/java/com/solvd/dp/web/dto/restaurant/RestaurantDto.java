package com.solvd.dp.web.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class RestaurantDto {

    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Length(max = 255, message = "Description must be less than {value} characters", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @NotEmpty(message = "Address is required", groups = {OnCreate.class})
    @Valid
    private List<AddressDto> addresses;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ItemDto> items;

}
