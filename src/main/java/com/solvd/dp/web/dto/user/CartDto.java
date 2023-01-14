package com.solvd.dp.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.web.dto.restaurant.ItemDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.Map;

@Data
public class CartDto {

    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<ItemDto, Long> items;

}
