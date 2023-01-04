package com.example.dp.web.dto.restaurant;

import com.example.dp.domain.restaurant.EmployeePosition;
import lombok.Data;

@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private EmployeePosition position;
    private RestaurantDto restaurant;
}
