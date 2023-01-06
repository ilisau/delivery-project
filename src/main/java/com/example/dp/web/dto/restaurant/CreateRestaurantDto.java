package com.example.dp.web.dto.restaurant;

import com.example.dp.web.dto.user.CreateAddressDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import static com.example.dp.config.ApplicationConstants.*;

@Data
public class CreateRestaurantDto {

    @NotNull(message = "Name is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Name must be between {min} and {max} characters")
    private String name;

    @Max(value = MAX_BIG_FIELD_LENGTH, message = "Description must be less than {value} characters")
    private String description;

    @NotEmpty(message = "Address is required")
    private List<CreateAddressDto> addresses;
}
