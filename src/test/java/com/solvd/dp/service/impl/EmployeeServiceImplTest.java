package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Employee;
import com.solvd.dp.domain.restaurant.EmployeePosition;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.repository.EmployeeRepository;
import com.solvd.dp.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Employee employee = new Employee();
        employee.setId(id);

        when(employeeRepository.findById(id))
                .thenReturn(Optional.of(employee));

        assertEquals(employee, employeeService.getById(id));
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(employeeRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getById(id));
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void getByExistingEmail() {
        String email = "john@gmail.com";
        Employee employee = new Employee();
        employee.setEmail(email);

        when(employeeRepository.findByEmail(email))
                .thenReturn(Optional.of(employee));

        assertEquals(employee, employeeService.getByEmail(email));
        verify(employeeRepository, times(1)).findByEmail(email);
    }

    @Test
    void getByNotExistingEmail() {
        String email = "john@gmail.com";

        when(employeeRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getByEmail(email));
        verify(employeeRepository, times(1)).findByEmail(email);
    }

    @Test
    void getAllByRestaurantId() {
        Long id = 1L;
        List<Employee> employees = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setId((long) i);
            employees.add(employee);
        }

        when(employeeRepository.getAllByRestaurantId(id))
                .thenReturn(employees);

        assertEquals(employees, employeeService.getAllByRestaurantId(id));
        verify(employeeRepository, times(1)).getAllByRestaurantId(id);
    }

    @Test
    void getAllByRestaurantIdAndPosition() {
        Long id = 1L;
        EmployeePosition position = EmployeePosition.COOK;
        List<Employee> employees = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Employee employee = new Employee();
            employee.setId((long) i);
            employee.setPosition(position);
            employees.add(employee);
        }

        when(employeeRepository.getAllByRestaurantIdAndPosition(id, position))
                .thenReturn(employees);

        assertEquals(employees, employeeService.getAllByRestaurantIdAndPosition(id, position));
        verify(employeeRepository, times(1)).getAllByRestaurantIdAndPosition(id, position);
    }

    @Test
    void update() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmail("john@gmail.com");
        employee.setPassword("123456");

        employeeService.update(employee);

        assertTrue(passwordEncoder.matches("123456", employee.getPassword()));
        verify(employeeRepository, times(1)).update(employee);
    }

    @Test
    void createNotExisting() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setPassword("123456");
        employee.setPasswordConfirmation("123456");

        when(employeeRepository.exists(employee, 1L))
                .thenReturn(false);

        doAnswer(invocation -> {
            Employee employee1 = invocation.getArgument(0);
            employee1.setId(1L);
            return employee1;
        }).when(employeeRepository).create(employee);

        employeeService.create(employee, 1L);

        assertTrue(employee.getRoles().contains(Role.ROLE_EMPLOYEE));
        assertTrue(passwordEncoder.matches("123456", employee.getPassword()));
        verify(employeeRepository, times(1)).exists(employee, 1L);
        verify(employeeRepository, times(1)).create(employee);
        verify(employeeRepository, times(1)).saveRoles(employee.getId(), employee.getRoles());
        verify(restaurantService, times(1)).addEmployeeById(1L, employee.getId());
    }

    @Test
    void createNotExistingWithMismatchingPasswordConfirmation() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setPassword("123456");
        employee.setPasswordConfirmation("1234567");

        when(employeeRepository.exists(employee, 1L))
                .thenReturn(false);

        assertThrows(IllegalStateException.class, () -> employeeService.create(employee, 1L));
        verify(employeeRepository, times(1)).exists(employee, 1L);
        verify(employeeRepository, times(0)).create(employee);
        verify(employeeRepository, times(0)).saveRoles(employee.getId(), employee.getRoles());
        verify(restaurantService, times(0)).addEmployeeById(1L, employee.getId());
    }

    @Test
    void createExisting() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setPassword("123456");
        employee.setPasswordConfirmation("123456");

        when(employeeRepository.exists(employee, 1L))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> employeeService.create(employee, 1L));
        verify(employeeRepository, times(1)).exists(employee, 1L);
        verify(employeeRepository, times(0)).create(employee);
        verify(employeeRepository, times(0)).saveRoles(employee.getId(), employee.getRoles());
        verify(restaurantService, times(0)).addEmployeeById(1L, employee.getId());
    }

    @Test
    void existsWithExistingEmployee() {
        Long employeeId = 1L;
        Long restaurantId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.exists(employee, restaurantId))
                .thenReturn(true);

        assertTrue(employeeService.exists(employeeId, restaurantId));
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).exists(employee, restaurantId);
    }

    @Test
    void existsWithNotExistingEmployee() {
        Long employeeId = 1L;
        Long restaurantId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.exists(employeeId, restaurantId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    void isManager() {
        Long employeeId = 1L;
        Long restaurantId = 1L;

        when(employeeRepository.isManager(employeeId, restaurantId))
                .thenReturn(true);

        assertTrue(employeeService.isManager(employeeId, restaurantId));
        verify(employeeRepository, times(1)).isManager(employeeId, restaurantId);
    }

    @Test
    void delete() {
        Long id = 1L;

        employeeService.delete(id);
        verify(employeeRepository, times(1)).delete(id);
    }
}