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
        verify(courierRepository, times(1)).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(courierRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courierService.getById(id));
        verify(courierRepository, times(1)).findById(id);
    }

    @Test
    void getByExistingEmail() {
        String email = "john@gmail.com";
        Courier courier = new Courier();
        courier.setEmail(email);

        when(courierRepository.findByEmail(email))
                .thenReturn(Optional.of(courier));

        assertEquals(courier, courierService.getByEmail(email));
        verify(courierRepository, times(1)).findByEmail(email);
    }

    @Test
    void getByNotExistingEmail() {
        String email = "john@gmail.com";

        when(courierRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courierService.getByEmail(email));
        verify(courierRepository, times(1)).findByEmail(email);
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
        verify(courierRepository, times(1)).findByOrderId(orderId);
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
        verify(courierRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void getAll() {
        List<Courier> couriers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Courier courier = new Courier();
            courier.setId((long) i);
            couriers.add(courier);
        }

        when(courierRepository.getAll())
                .thenReturn(couriers);

        assertEquals(couriers, courierService.getAll());
        verify(courierRepository, times(1)).getAll();
    }

    @Test
    void getAllByStatus() {
        List<Courier> couriers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Courier courier = new Courier();
            courier.setId((long) i);
            courier.setStatus(CourierStatus.AVAILABLE);
            couriers.add(courier);
        }

        when(courierRepository.getAllByStatus(CourierStatus.AVAILABLE))
                .thenReturn(couriers);

        assertEquals(couriers, courierService.getAllByStatus(CourierStatus.AVAILABLE));
        verify(courierRepository, times(1)).getAllByStatus(CourierStatus.AVAILABLE);
    }

    @Test
    void updateNotExisting() {
        Courier courier = new Courier();
        courier.setId(1L);
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPassword("123456");

        when(courierRepository.exists(courier))
                .thenReturn(false);

        doAnswer(invocation -> {
            Courier courier1 = invocation.getArgument(0);
            courier.setFirstName("John");
            courier.setLastName("Doe");
            return courier1;
        }).when(courierRepository).update(courier);

        courierService.update(courier);

        assertEquals("John", courier.getFirstName());
        assertEquals("Doe", courier.getLastName());
        assertTrue(passwordEncoder.matches("123456", courier.getPassword()));
        verify(courierRepository, times(1)).exists(courier);
        verify(courierRepository, times(1)).update(courier);
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
        verify(courierRepository, times(1)).exists(courier);
        verify(courierRepository, times(0)).update(courier);

    }

    @Test
    void createNotExisting() {
        Courier courier = new Courier();
        courier.setFirstName("John");
        courier.setLastName("Doe");
        courier.setPassword("123456");
        courier.setPasswordConfirmation("123456");

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
        assertTrue(passwordEncoder.matches("123456", courier.getPassword()));
        assertNotNull(courier.getCreatedAt());
        verify(courierRepository, times(1)).exists(courier);
        verify(courierRepository, times(1)).create(courier);
        verify(courierRepository, times(1)).saveRoles(courier.getId(), courier.getRoles());
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
        verify(courierRepository, times(0)).create(courier);
        verify(courierRepository, times(0)).saveRoles(anyLong(), anySet());
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
        verify(courierRepository, times(1)).exists(courier);
        verify(courierRepository, times(0)).create(courier);
        verify(courierRepository, times(0)).saveRoles(anyLong(), anySet());
    }

    @Test
    void assignUnassignedOrder() {
        Long courierId = 1L;
        Long orderId = 1L;

        when(orderService.isOrderAssigned(orderId))
                .thenReturn(false);

        courierService.assignOrder(courierId, orderId);

        verify(orderService, times(1)).isOrderAssigned(orderId);
        verify(orderService, times(1)).assignOrder(orderId, courierId);
    }

    @Test
    void assignAssignedOrder() {
        Long courierId = 1L;
        Long orderId = 1L;

        when(orderService.isOrderAssigned(orderId))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> courierService.assignOrder(courierId, orderId));
        verify(orderService, times(1)).isOrderAssigned(orderId);
        verify(orderService, times(0)).assignOrder(orderId, courierId);
    }

    @Test
    void delete() {
        Long id = 1L;
        courierService.delete(id);

        verify(courierRepository, times(1)).delete(id);
    }
}