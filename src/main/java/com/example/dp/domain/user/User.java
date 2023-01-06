package com.example.dp.domain.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class User {

    protected Long id;
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected List<Address> addresses;
    protected Cart cart;
    protected LocalDate createdAt;

    public User() {
        this.addresses = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
