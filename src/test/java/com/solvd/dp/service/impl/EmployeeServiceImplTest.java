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
import java.util.stream.Stream;

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
        verify(employeeRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(employeeRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getById(id));
        verify(employeeRepository).findById(id);
    }

    @Test
    void getByExistingEmail() {
        String email = "john@gmail.com";
        Employee employee = new Employee();
        employee.setEmail(email);

        when(employeeRepository.findByEmail(email))
                .thenReturn(Optional.of(employee));

        assertEquals(employee, employeeService.getByEmail(email));
        verify(employeeRepository).findByEmail(email);
    }

    @Test
    void getByNotExistingEmail() {
        String email = "john@gmail.com";

        when(employeeRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getByEmail(email));
        verify(employeeRepository).findByEmail(email);
    }

    @Test
    void getAllByRestaurantId() {
        Long id = 1L;
        List<Employee> employees = new ArrayList<>();
        Stream.iterate(1, i -> i + 1)
                .limit(5)
                .forEach(i -> {
                    Employee employee = new Employee();
                    employee.setId((long) i);
                    employees.add(employee);
                });

        when(employeeRepository.getAllByRestaurantId(id))
                .thenReturn(employees);

        assertEquals(employees, employeeService.getAllByRestaurantId(id));
        verify(employeeRepository).getAllByRestaurantId(id);
    }

    @Test
    void getAllByRestaurantIdAndPosition() {
        Long id = 1L;
        EmployeePosition position = EmployeePosition.COOK;
        List<Employee> employees = new ArrayList<>();
        Stream.iterate(1, i -> i + 1)
                .limit(5)
                .forEach(i -> {
                    Employee employee = new Employee();
                    employee.setId((long) i);
                    employees.add(employee);
                });

        when(employeeRepository.getAllByRestaurantIdAndPosition(id, position))
                .thenReturn(employees);

        assertEquals(employees, employeeService.getAllByRestaurantIdAndPosition(id, position));
        verify(employeeRepository).getAllByRestaurantIdAndPosition(id, position);
    }

    @Test
    void update() {
        String password = "123456";
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmail("john@gmail.com");
        employee.setPassword(password);

        employeeService.update(employee);

        assertTrue(passwordEncoder.matches(password, employee.getPassword()));
        verify(employeeRepository).update(employee);
    }

    @Test
    void createNotExisting() {
        String password = "123456";
        Employee employee = new Employee();
        employee.setName("John");
        employee.setPassword(password);
        employee.setPasswordConfirmation(password);

        when(employeeRepository.exists(employee, 1L))
                .thenReturn(false);

        doAnswer(invocation -> {
            Employee employee1 = invocation.getArgument(0);
            employee1.setId(1L);
            return employee1;
        }).when(employeeRepository).create(employee);

        employeeService.create(employee, 1L);

        assertTrue(employee.getRoles().contains(Role.ROLE_EMPLOYEE));
        assertTrue(passwordEncoder.matches(password, employee.getPassword()));
        verify(employeeRepository).exists(employee, 1L);
        verify(employeeRepository).create(employee);
        verify(employeeRepository).saveRoles(employee.getId(), employee.getRoles());
        verify(restaurantService).addEmployeeById(1L, employee.getId());
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
        verify(employeeRepository).exists(employee, 1L);
        verify(employeeRepository, never()).create(employee);
        verify(employeeRepository, never()).saveRoles(employee.getId(), employee.getRoles());
        verify(restaurantService, never()).addEmployeeById(1L, employee.getId());
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
        verify(employeeRepository).exists(employee, 1L);
        verify(employeeRepository, never()).create(employee);
        verify(employeeRepository, never()).saveRoles(employee.getId(), employee.getRoles());
        verify(restaurantService, never()).addEmployeeById(1L, employee.getId());
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
        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).exists(employee, restaurantId);
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
        verify(employeeRepository).findById(employeeId);
    }

    @Test
    void isManager() {
        Long employeeId = 1L;
        Long restaurantId = 1L;

        when(employeeRepository.isManager(employeeId, restaurantId))
                .thenReturn(true);

        assertTrue(employeeService.isManager(employeeId, restaurantId));
        verify(employeeRepository).isManager(employeeId, restaurantId);
    }

    @Test
    void delete() {
        Long id = 1L;

        employeeService.delete(id);
        verify(employeeRepository).delete(id);
    }
}