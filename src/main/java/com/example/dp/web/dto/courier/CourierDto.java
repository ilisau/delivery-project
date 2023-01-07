package com.example.dp.web.dto.courier;

import com.example.dp.domain.courier.CourierStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourierDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;
    private CourierStatus status;
    private String phoneNumber;
}
