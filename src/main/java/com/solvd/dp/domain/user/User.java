package com.solvd.dp.domain.user;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String phoneNumber;
    private List<Address> addresses;
    private List<Order> orders;
    private Cart cart;
    private LocalDateTime createdAt;
    private Set<Role> roles;

}
