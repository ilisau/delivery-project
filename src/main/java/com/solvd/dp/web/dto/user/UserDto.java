package com.solvd.dp.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User ID", example = "1")
    @Null(message = "Id must be null", groups = {OnCreate.class})
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Long id;

    @Schema(description = "User first name", example = "John Doe")
    @NotNull(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Username must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "User email", example = "johndoe@gmail.com")
    @NotNull(message = "Email is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Email must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email must be valid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @Schema(description = "User phone number", example = "123456789")
    @NotNull(message = "Phone number is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String phoneNumber;

    @Schema(description = "User password", example = "123456")
    @NotNull(message = "Password is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 6, message = "Password must be longer than {min} characters", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "User password confirmation", example = "123456")
    @NotNull(message = "Password confirmation is required", groups = {OnCreate.class})
    @Length(min = 6, message = "Password confirmation must be longer than {min} characters", groups = {OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;

    @Schema(description = "User addresses")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AddressDto> addresses;

    @Schema(description = "User orders")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<OrderDto> orders;

    @Schema(description = "User cart")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CartDto cart;

    @Schema(description = "User created time", example = "2021-01-01T00:00:00")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdAt;

}
