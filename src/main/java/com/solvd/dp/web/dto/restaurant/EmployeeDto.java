package com.solvd.dp.web.dto.restaurant;

import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EmployeeDto {

    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Position is required", groups = {OnCreate.class, OnUpdate.class})
    private EmployeePosition position;

}
