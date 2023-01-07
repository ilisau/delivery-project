package com.example.dp.web.dto.restaurant;

import com.example.dp.web.dto.user.AddressDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class CreateRestaurantDto {

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters")
    private String name;

    @Max(value = 255, message = "Description must be less than {value} characters")
    private String description;

    @NotEmpty(message = "Address is required")
    private List<AddressDto> addresses;
}
