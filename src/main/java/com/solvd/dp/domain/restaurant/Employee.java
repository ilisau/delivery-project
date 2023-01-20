package com.solvd.dp.domain.restaurant;

import com.solvd.dp.domain.user.Role;
import lombok.Data;

import java.util.Set;

@Data
public class Employee {

    private Long id;
    private String name;
    private EmployeePosition position;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;

}
