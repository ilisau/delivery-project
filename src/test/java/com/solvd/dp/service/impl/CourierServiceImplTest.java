package com.solvd.dp.service.impl;

import com.solvd.dp.domain.courier.Courier;
import com.solvd.dp.domain.courier.CourierStatus;
import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.repository.CourierRepository;
import com.solvd.dp.service.OrderService;
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
class CourierServiceImplTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CourierServiceImpl courierService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Courier courier = new Courier();
        courier.setId(id);

        when(courierRepository.findById(id))
                .thenReturn(Optional.of(courier));

        assertEquals(courier, courierService.getById(id));
        verify(courierRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(courierRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courierService.getById(id));
        verify(courierRepository).findById(id);
    }

    @Test
    void getByExistingEmail() {
        String email = "john@gmail.com";
        Courier courier = new Courier();
        courier.setEmail(email);

        when(courierRepository.findByEmail(email))
                .thenReturn(Optional.of(courier));

        assertEquals(courier, courierService.getByEmail(email));
        verify(courierRepository).findByEmail(email);
    }

    @Test
    void getByNotExistingEmail() {
        String email = "john@gmail.com";

        when(courierRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courierService.getByEmail(email));
        verify(courierRepository).findByEmail(email);
    }

    @Test
    void getByExistingOrderId() {
        Long courierId = 1L;
        Long orderId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);

        when(courierRepository.findByOrderId(orderId))
                .thenReturn(Optional.of(courier));

        assertEquals(courier, courierService.getByOrderId(orderId));
        verify(courierRepository).findByOrderId(orderId);
    }

    @Test
    void getByNotExistingOrderId() {
        Long courierId = 1L;
        Long orderId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);

        when(courierRepository.findByOrderId(orderId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courierService.getByOrderId(orderId));
        verify(courierRepository).findByOrderId(orderId);
    }

    @Test
    void getAll() {
        List<Courier> couriers = new ArrayList<>();
        Stream.iterate(1L, i -> i + 1)
                .limit(10)
                .forEach(i -> {
                    Courier courier = new Courier();
                    courier.setId(i);
                    couriers.add(courier);
                });

        when(courierRepository.getAll())
                .thenReturn(couriers);

        assertEquals(couriers, courierService.getAll());
        verify(courierRepository).getAll();
    }

    @Test
    void getAllByStatus() {
        CourierStatus status = CourierStatus.AVAILABLE;
        List<Courier> couriers = new ArrayList<>();
        Stream.iterate(1L, i -> i + 1)
                .limit(10)
                .forEach(i -> {
                    Courier courier = new Courier();
                    courier.setId(i);
                    courier.setStatus(status);
                    couriers.add(courier);
                });

        when(courierRepository.getAllByStatus(status))
                .thenReturn(couriers);

        assertEquals(couriers, courierService.getAllByStatus(status));
        verify(courierRepository).getAllByStatus(status);
    }

    @Test
    void updateNotExisting() {
        String firstName = "John";
        String lastName = "Doe";
        String password = "123456";
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setFirstName(firstName);
        courier.setLastName(lastName);
        courier.setPassword(password);

        when(courierRepository.exists(courier))
                .thenReturn(false);

        doAnswer(invocation -> {
            Courier courier1 = invocation.getArgument(0);
            courier.setFirstName(firstName);
            courier.setLastName(lastName);
            return courier1;
        }).when(courierRepository).update(courier);

        courierService.update(courier);

        assertEquals(firstName, courier.getFirstName());
        assertEquals(lastName, courier.getLastName());
        assertTrue(passwordEncoder.matches(password, courier.getPassword()));
        verify(courierRepository).exists(courier);
        verify(courierRepository).update(courier);
    }

    @Test
    void updateExisting() {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPassword("123456");

        when(courierRepository.exists(courier))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> courierService.update(courier));
        verify(courierRepository).exists(courier);
        verify(courierRepository, never()).update(courier);

    }

    @Test
    void createNotExisting() {
        String password = "123456";
        Courier courier = new Courier();
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPassword("123456");
        courier.setPasswordConfirmation(password);

        when(courierRepository.exists(courier))
                .thenReturn(false);

        doAnswer(invocation -> {
            Courier courier1 = invocation.getArgument(0);
            courier1.setId(1L);
            return courier1;
        }).when(courierRepository).create(courier);

        courierService.create(courier);

        assertTrue(courier.getRoles().contains(Role.ROLE_COURIER));
        assertEquals(courier.getStatus(), CourierStatus.AVAILABLE);
        assertTrue(passwordEncoder.matches(password, courier.getPassword()));
        assertNotNull(courier.getCreatedAt());
        verify(courierRepository).exists(courier);
        verify(courierRepository).create(courier);
        verify(courierRepository).saveRoles(courier.getId(), courier.getRoles());
    }

    @Test
    void createNotExistingWithMismatchingPasswordConfirmation() {
        Courier courier = new Courier();
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPassword("123456");
        courier.setPasswordConfirmation("1234567");

        when(courierRepository.exists(courier))
                .thenReturn(false);

        assertThrows(IllegalStateException.class, () -> courierService.create(courier));
        verify(courierRepository, never()).create(courier);
        verify(courierRepository, never()).saveRoles(anyLong(), anySet());
    }

    @Test
    void createExisting() {
        Courier courier = new Courier();
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPassword("123456");
        courier.setPasswordConfirmation("123456");

        when(courierRepository.exists(courier))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> courierService.create(courier));
        verify(courierRepository).exists(courier);
        verify(courierRepository, never()).create(courier);
        verify(courierRepository, never()).saveRoles(anyLong(), anySet());
    }

    @Test
    void assignUnassignedOrder() {
        Long courierId = 1L;
        Long orderId = 1L;

        when(orderService.isOrderAssigned(orderId))
                .thenReturn(false);

        courierService.assignOrder(courierId, orderId);

        verify(orderService).isOrderAssigned(orderId);
        verify(orderService).assignOrder(orderId, courierId);
    }

    @Test
    void assignAssignedOrder() {
        Long courierId = 1L;
        Long orderId = 1L;

        when(orderService.isOrderAssigned(orderId))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> courierService.assignOrder(courierId, orderId));
        verify(orderService).isOrderAssigned(orderId);
        verify(orderService, never()).assignOrder(orderId, courierId);
    }

    @Test
    void delete() {
        Long id = 1L;
        courierService.delete(id);

        verify(courierRepository).delete(id);
    }
}