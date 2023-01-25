package com.solvd.dp.service.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.CourierService;
import com.solvd.dp.service.EmployeeService;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.auth.JwtRequest;
import com.solvd.dp.web.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private CourierService courierService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void loginExistingUser() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("john@gmail.com");
        jwtRequest.setPassword("123456");

        User user = new User();
        user.setId(1L);
        user.setRoles(Set.of(Role.ROLE_USER));

        when(userService.getByEmail(jwtRequest.getUsername()))
                .thenReturn(user);

        authenticationService.loginUser(jwtRequest);

        verify(userService, times(1)).getByEmail(jwtRequest.getUsername());
        verify(jwtTokenProvider, times(1)).createUserToken(eq(user.getId()), eq(jwtRequest.getUsername()), anyList());
        verify(jwtTokenProvider, times(1)).createUserRefreshToken(eq(user.getId()), eq(jwtRequest.getUsername()));
    }

    @Test
    void refreshUserTokens() {
        authenticationService.refreshUserTokens("token");

        verify(jwtTokenProvider, times(1)).refreshUserTokens(eq("token"));
    }

    @Test
    void loginCourier() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("john@gmail.com");
        jwtRequest.setPassword("123456");

        Courier courier = new Courier();
        courier.setId(1L);
        courier.setRoles(Set.of(Role.ROLE_COURIER));

        when(courierService.getByEmail(jwtRequest.getUsername()))
                .thenReturn(courier);

        authenticationService.loginCourier(jwtRequest);

        verify(courierService, times(1)).getByEmail(jwtRequest.getUsername());
        verify(jwtTokenProvider, times(1)).createCourierToken(eq(courier.getId()), eq(jwtRequest.getUsername()), anyList());
        verify(jwtTokenProvider, times(1)).createCourierRefreshToken(eq(courier.getId()), eq(jwtRequest.getUsername()));
    }

    @Test
    void refreshCourierTokens() {
        authenticationService.refreshCourierTokens("token");

        verify(jwtTokenProvider, times(1)).refreshCourierTokens(eq("token"));
    }

    @Test
    void loginEmployee() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("john@gmail.com");
        jwtRequest.setPassword("123456");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setRoles(Set.of(Role.ROLE_COURIER));

        when(employeeService.getByEmail(jwtRequest.getUsername()))
                .thenReturn(employee);

        authenticationService.loginEmployee(jwtRequest);

        verify(employeeService, times(1)).getByEmail(jwtRequest.getUsername());
        verify(jwtTokenProvider, times(1)).createEmployeeToken(eq(employee.getId()), eq(jwtRequest.getUsername()), anyList());
        verify(jwtTokenProvider, times(1)).createEmployeeRefreshToken(eq(employee.getId()), eq(jwtRequest.getUsername()));
    }

    @Test
    void refreshEmployeeTokens() {
        authenticationService.refreshEmployeeTokens("token");

        verify(jwtTokenProvider, times(1)).refreshEmployeeTokens(eq("token"));
    }
}