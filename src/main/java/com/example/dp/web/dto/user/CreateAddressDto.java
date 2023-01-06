package com.example.dp.web.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.example.dp.config.ApplicationConstants.MAX_FIELD_LENGTH;
import static com.example.dp.config.ApplicationConstants.MIN_FIELD_LENGTH;

@Data
public class CreateAddressDto {

    @NotNull(message = "Street name is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Street name must be between {min} and {max} characters")
    private String streetName;

    @NotNull(message = "House number is required")
    @Min(value = 1, message = "House number must be greater than {value}")
    private Integer houseNumber;

    @Min(value = -2, message = "Floor number must be greater than {value}")
    private Integer floorNumber;

    @Min(value = 1, message = "Flat number must be greater than {value}")
    private Integer flatNumber;
}
