package com.solvd.dp.web.dto.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class CourierDto {

    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "First name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "First name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;

    @NotNull(message = "Last name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Last name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastActiveAt;

    @NotNull(message = "Status is required", groups = {OnUpdate.class})
    private CourierStatus status;

    @NotNull(message = "Phone number is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;

    @NotNull(message = "Email is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Email must be between {min} and {max} characters")
    @Email(message = "Email must be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @NotNull(message = "Password is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Password must be between {min} and {max} characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Password confirmation is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Password confirmation must be between {min} and {max} characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

}
