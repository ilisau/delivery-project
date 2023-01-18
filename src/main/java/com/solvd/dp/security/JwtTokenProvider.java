package com.solvd.dp.security;

import com.solvd.dp.domain.exception.AccessDeniedException;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.auth.JwtResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final UserService userService;

    @Value("${jwt.token.accessToken}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.token.refreshToken}")
    private long refreshTokenValidityInMilliseconds;

    private final UserDetailsService userDetailsService;

    private Key key;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(Long userId, String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        claims.put("userRoles", roles);
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (validateToken(refreshToken)) {
            Long userId = Long.valueOf(getUserId(refreshToken));
            User user = userService.getById(userId);
            jwtResponse.setUserId(user.getId());
            jwtResponse.setUsername(user.getEmail());
            jwtResponse.setToken(createToken(user.getId(), user.getEmail(), resolveRoles(new ArrayList<>(user.getRoles()))));
            jwtResponse.setRefreshToken(createRefreshToken(userId, user.getEmail()));
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
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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

    public String getUserId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId")
                .toString();
    }

    public String getRoles(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userRoles")
                .toString();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
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
