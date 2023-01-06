package com.example.dp.web.dto.restaurant;

import com.example.dp.domain.restaurant.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.example.dp.config.ApplicationConstants.*;

@Data
public class CreateItemDto {

    @NotNull(message = "Name is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Name must be between {min} and {max} characters")
    private String name;

    @Length(max = MAX_BIG_FIELD_LENGTH, message = "Description must be less than {max} characters")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to {value}")
    private Double price;

    @NotNull(message = "Type is required")
    private ItemType type;

    @NotNull(message = "Restaurant id is required")
    @Min(value = 1, message = "Restaurant id must be greater than or equal to {value}")
    private Long restaurantId;
}
