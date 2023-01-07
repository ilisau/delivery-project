package com.example.dp.web.dto.restaurant;

import com.example.dp.domain.restaurant.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class CreateItemDto {

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters")
    private String name;

    @Length(max = 255, message = "Description must be less than {max} characters")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to {value}")
    private BigDecimal price;

    @NotNull(message = "Type is required")
    private ItemType type;

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than or equal to {value}")
    private Long restaurantId;
}
