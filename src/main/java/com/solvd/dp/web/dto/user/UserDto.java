package com.solvd.dp.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {

    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Username must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Email is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Email must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email must be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @NotNull(message = "Phone number is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String phoneNumber;

    @NotNull(message = "Password is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 6, message = "Password must be longer than {min} characters", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Password confirmation is required", groups = {OnCreate.class})
    @Length(min = 6, message = "Password confirmation must be longer than {min} characters", groups = {OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AddressDto> addresses;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<OrderDto> orders;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CartDto cart;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdAt;

}
