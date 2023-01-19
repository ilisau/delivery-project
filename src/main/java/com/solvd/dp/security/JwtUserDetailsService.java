package com.solvd.dp.security;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final CourierService courierService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        String flag = username.substring(0, 2);
        username = username.substring(2);
        switch (flag) {
            case "U:" -> {
                User user = userService.getByEmail(username);
                return JwtEntityFactory.create(user);
            }
            case "C:" -> {
                Courier courier = courierService.getByEmail(username);
                return JwtEntityFactory.create(courier);
            }
            default -> throw new IllegalArgumentException("User with email " + username + " not found");
        }
    }

}
