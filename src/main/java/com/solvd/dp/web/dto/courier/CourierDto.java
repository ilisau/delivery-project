package com.solvd.dp.web.dto.courier;

import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
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

    private LocalDateTime createdAt;

    private LocalDateTime lastActiveAt;

    @Null(message = "Status must be null", groups = {OnCreate.class})
    @NotNull(message = "Status is required", groups = {OnUpdate.class})
    private CourierStatus status;

    @NotNull(message = "Phone number is required", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 45, message = "Phone number must be between {min} and {max} characters")
    private String phoneNumber;

}
