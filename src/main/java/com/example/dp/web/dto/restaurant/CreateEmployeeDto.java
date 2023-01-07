package com.example.dp.web.dto.restaurant;

import com.example.dp.domain.restaurant.EmployeePosition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateEmployeeDto {

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters")
    private String name;

    @NotNull(message = "Position is required")
    private EmployeePosition position;

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than {value}")
    private Long restaurantId;
}
