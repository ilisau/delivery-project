package com.solvd.dp.web.security;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.exception.AccessDeniedException;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.service.UserService;
import com.solvd.dp.service.properties.JwtProperties;
import com.solvd.dp.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final CourierService courierService;
    private final EmployeeService employeeService;
    private final Key key;

    public JwtTokenProvider(JwtProperties jwtProperties, UserDetailsService userDetailsService, UserService userService, CourierService courierService, EmployeeService employeeService) {
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.courierService = courierService;
        this.employeeService = employeeService;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createUserToken(Long userId, String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("type", "U");
        claims.put("id", userId);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String createUserRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (validateToken(refreshToken)) {
            Long userId = Long.valueOf(getId(refreshToken));
            User user = userService.getById(userId);
            jwtResponse.setEntityId(user.getId());
            jwtResponse.setUsername(user.getEmail());
            jwtResponse.setToken(createUserToken(user.getId(), user.getEmail(), resolveRoles(new ArrayList<>(user.getRoles()))));
            jwtResponse.setRefreshToken(createUserRefreshToken(userId, user.getEmail()));
            return jwtResponse;
        } else {
            throw new AccessDeniedException("Invalid refresh token");
        }
    }

    public String createCourierToken(Long courierId, String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("type", "C");
        claims.put("id", courierId);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String createCourierRefreshToken(Long courierId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", courierId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshCourierTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (validateToken(refreshToken)) {
            Long courierId = Long.valueOf(getId(refreshToken));
            Courier courier = courierService.getById(courierId);
            jwtResponse.setEntityId(courier.getId());
            jwtResponse.setUsername(courier.getEmail());
            jwtResponse.setToken(createCourierToken(courier.getId(), courier.getEmail(), resolveRoles(new ArrayList<>(courier.getRoles()))));
            jwtResponse.setRefreshToken(createCourierRefreshToken(courierId, courier.getEmail()));
            return jwtResponse;
        } else {
            throw new AccessDeniedException("Invalid refresh token");
        }
    }

    public String createEmployeeToken(Long employeeId, String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("type", "E");
        claims.put("id", employeeId);
        claims.put("roles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String createEmployeeRefreshToken(Long employeeId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", employeeId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshEmployeeTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (validateToken(refreshToken)) {
            Long employeeId = Long.valueOf(getId(refreshToken));
            Employee employee = employeeService.getById(employeeId);
            jwtResponse.setEntityId(employee.getId());
            jwtResponse.setUsername(employee.getEmail());
            jwtResponse.setToken(createCourierToken(employee.getId(), employee.getEmail(), resolveRoles(new ArrayList<>(employee.getRoles()))));
            jwtResponse.setRefreshToken(createCourierRefreshToken(employeeId, employee.getEmail()));
            return jwtResponse;
        } else {
            throw new AccessDeniedException("Invalid refresh token");
        }
    }

    private List<String> resolveRoles(List<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public Authentication getAuthentication(String token) {
        String type = getType(token);
        switch (type) {
            case "U" -> {
                UserDetails userDetails = userDetailsService.loadUserByUsername("U:" + getUsername(token));
                return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
            }
            case "C" -> {
                UserDetails courierDetails = userDetailsService.loadUserByUsername("C:" + getUsername(token));
                return new UsernamePasswordAuthenticationToken(courierDetails, "", courierDetails.getAuthorities());
            }
            case "E" -> {
                UserDetails employeeDetails = userDetailsService.loadUserByUsername("E:" + getUsername(token));
                return new UsernamePasswordAuthenticationToken(employeeDetails, "", employeeDetails.getAuthorities());
            }
            default -> throw new AccessDeniedException("Invalid token");
        }
    }

    public String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getType(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type")
                .toString();
    }

    public String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    public String getRoles(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles")
                .toString();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
