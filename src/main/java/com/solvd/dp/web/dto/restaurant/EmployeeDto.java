package com.solvd.dp.web.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "Employee DTO")
public class EmployeeDto {

    @Schema(description = "Employee id", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "Employee name", example = "John")
    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "Employee position", example = "COOK")
    @NotNull(message = "Position is required", groups = {OnCreate.class, OnUpdate.class})
    private EmployeePosition position;

    @Schema(description = "Employee email", example = "john@gmail.com")
    @NotNull(message = "Email is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Email must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    @Email
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @Schema(description = "Employee password", example = "123456")
    @NotNull(message = "Password is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Password must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "Employee password confirmation", example = "123456")
    @NotNull(message = "Password confirmation is required", groups = {OnCreate.class})
    @Length(min = 3, max = 45, message = "Password confirmation must be between {min} and {max} characters", groups = {OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

}
