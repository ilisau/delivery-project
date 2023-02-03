package com.solvd.dp.domain.courier;

import com.solvd.dp.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;
    private CourierStatus status;
    private String phoneNumber;
    private String email;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;

}
