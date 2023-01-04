package com.example.dp.domain.courier;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Courier {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate createdAt;
    private LocalDateTime lastActiveAt;
    private CourierStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return id.equals(courier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
