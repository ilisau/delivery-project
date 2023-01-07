package com.example.dp.domain.restaurant;

import lombok.Data;

@Data
public class Employee {

    private Long id;
    private String name;
    private EmployeePosition position;
}
