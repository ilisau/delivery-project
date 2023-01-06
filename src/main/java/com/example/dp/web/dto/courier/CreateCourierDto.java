package com.example.dp.web.dto.courier;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.dp.config.ApplicationConstants.MAX_FIELD_LENGTH;
import static com.example.dp.config.ApplicationConstants.MIN_FIELD_LENGTH;

@Data
public class CreateCourierDto {

    @NotNull(message = "First name is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "First name must be between {min} and {max} characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Last name must be between {min} and {max} characters")
    private String lastName;

    @NotNull(message = "Phone number is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;
}
