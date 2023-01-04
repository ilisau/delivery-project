package com.example.dp.domain.restaurant;

import com.example.dp.domain.user.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Restaurant {

    private Long id;
    private String name;
    private String description;
    private List<Address> addresses;
    private List<Item> items;
    private List<Employee> employees;

    public Restaurant() {
        this.items = new ArrayList<>();
        this.addresses = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
