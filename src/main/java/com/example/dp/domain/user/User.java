package com.example.dp.domain.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    protected Long id;
    protected String name;
    protected String email;
    protected String password;
    protected String phoneNumber;
    protected List<Address> addresses;
    protected List<Order> orders;
    protected Cart cart;
    protected LocalDate createdAt;

    public User() {
        this.addresses = new ArrayList<>();
        this.orders = new ArrayList<>();
    }
}
