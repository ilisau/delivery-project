package com.example.dp.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.example.dp.config.ApplicationConstants.MAX_FIELD_LENGTH;
import static com.example.dp.config.ApplicationConstants.MIN_FIELD_LENGTH;

@Data
public class CreateUserDto {

    @NotNull(message = "Name is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Username must be between {min} and {max} characters")
    private String name;

    @NotNull(message = "Email is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Email must be between {min} and {max} characters")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @Length(min = MIN_FIELD_LENGTH, max = MAX_FIELD_LENGTH, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Password confirmation is required")
    private String passwordConfirmation;
}
