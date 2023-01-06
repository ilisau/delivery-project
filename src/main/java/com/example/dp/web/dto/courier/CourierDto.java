package com.example.dp.web.dto.courier;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourierDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private String phoneNumber;
    private LocalDate createdAt;
    private LocalDateTime lastActiveAt;
}
