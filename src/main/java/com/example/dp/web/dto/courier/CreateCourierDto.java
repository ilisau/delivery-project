package com.example.dp.web.dto.courier;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateCourierDto {

    @NotNull(message = "First name is required")
    @Length(min = 3, max = 45, message = "First name must be between {min} and {max} characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Length(min = 3, max = 45, message = "Last name must be between {min} and {max} characters")
    private String lastName;

    @NotNull(message = "Phone number is required")
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;
}
