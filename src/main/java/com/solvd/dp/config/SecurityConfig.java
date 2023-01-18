package com.solvd.dp.config;

import com.solvd.dp.security.JwtConfigurer;
import com.solvd.dp.security.JwtTokenProvider;
import com.solvd.dp.security.evaluators.UserPermissionEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public MethodSecurityExpressionHandler userExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler
                = new DefaultMethodSecurityExpressionHandler();
        UserPermissionEvaluator permissionEvaluator
                = new UserPermissionEvaluator();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        return http.build();
    }

}
