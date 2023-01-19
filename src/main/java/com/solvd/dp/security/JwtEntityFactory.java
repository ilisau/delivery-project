package com.solvd.dp.security;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    public static JwtEntity create(Courier courier) {
        return new JwtEntity(
                courier.getId(),
                courier.getEmail(),
                courier.getFirstName(),
                courier.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(courier.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new).
                collect(Collectors.toList());
    }

}
