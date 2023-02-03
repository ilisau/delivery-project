package com.solvd.dp.domain.restaurant;

import com.solvd.dp.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;
    private String name;
    private EmployeePosition position;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;

}
