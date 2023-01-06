package com.example.dp.domain.restaurant;

import lombok.Data;

import java.util.Objects;

@Data
public class Employee {

    private Long id;
    private String name;
    private EmployeePosition position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
