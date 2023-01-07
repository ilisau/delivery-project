package com.example.dp.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateUserDto {

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 45, message = "Username must be between {min} and {max} characters")
    private String name;

    @NotNull(message = "Email is required")
    @Length(min = 3, max = 45, message = "Email must be between {min} and {max} characters")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;

    @NotNull(message = "Password is required")
    @Length(min = 6, message = "Password must be longer than {min} characters")
    private String password;

    @NotNull(message = "Password confirmation is required")

    @Length(min = 6, message = "Password confirmation must be longer than {min} characters")
    private String passwordConfirmation;
}
