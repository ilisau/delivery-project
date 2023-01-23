package com.solvd.dp.web.dto.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Schema(description = "Courier DTO")
public class CourierDto {

    @Schema(description = "Courier ID", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Courier name", example = "John")
    @NotNull(message = "First name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "First name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;

    @Schema(description = "Courier last name", example = "Doe")
    @NotNull(message = "Last name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Last name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String lastName;

    @Schema(description = "Courier register time", example = "2021-01-01T00:00:00")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Courier last active time", example = "2021-01-01T00:00:00")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime lastActiveAt;

    @Schema(description = "Courier status", example = "AVAILABLE")
    @NotNull(message = "Status is required", groups = {OnUpdate.class})
    private CourierStatus status;

    @Schema(description = "Courier phone number", example = "+1234567890")
    @NotNull(message = "Phone number is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;

    @Schema(description = "Courier email", example = "johndoe@gmail.com")
    @NotNull(message = "Email is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Email must be between {min} and {max} characters")
    @Email(message = "Email must be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @Schema(description = "Courier password", example = "password")
    @NotNull(message = "Password is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Password must be between {min} and {max} characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "Courier password confirmation", example = "password")
    @NotNull(message = "Password confirmation is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Password confirmation must be between {min} and {max} characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

}
