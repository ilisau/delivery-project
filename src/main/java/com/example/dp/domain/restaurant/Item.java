package com.example.dp.domain.restaurant;

import lombok.Data;

import java.util.Objects;

@Data
public class Item {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private ItemType type;
    private Boolean available;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
