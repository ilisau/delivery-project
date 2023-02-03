package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.Role;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.repository.UserRepository;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.CartService;
import com.solvd.dp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartService cartService;

    @Mock
    private OrderService orderService;

    @Mock
    private AddressService addressService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        assertEquals(user, userService.getById(id));
        verify(userRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(id));
        verify(userRepository).findById(id);
    }

    @Test
    void getByExistingEmail() {
        String email = "john@gmail.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        assertEquals(user, userService.getByEmail(email));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void getByNotExistingEmail() {
        String email = "john@gmail.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail(email));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void getByExistingPhoneNumber() {
        String phoneNumber = "123456789";
        User user = new User();
        user.setPhoneNumber(phoneNumber);

        when(userRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.of(user));

        assertEquals(user, userService.getByPhoneNumber(phoneNumber));
        verify(userRepository).findByPhoneNumber(phoneNumber);
    }

    @Test
    void getByNotExistingPhoneNumber() {
        String phoneNumber = "123456789";

        when(userRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByPhoneNumber(phoneNumber));
        verify(userRepository).findByPhoneNumber(phoneNumber);
    }

    @Test
    void updateExistingUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@gmail.com");

        when(userRepository.exists(user))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.update(user));
        verify(userRepository).exists(user);
        verify(userRepository, never()).update(user);
    }

    @Test
    void updateNotExistingUser() {
        String name = "John";
        String email = "john@gmail.com";
        String password = "123456";

        User user = new User();
        user.setId(1L);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.exists(user))
                .thenReturn(false);

        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setName(name);
            u.setEmail(email);
            return u;
        }).when(userRepository).update(user);

        userService.update(user);

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        verify(userRepository).exists(user);
        verify(userRepository).update(user);
    }

    @Test
    void createNotExistingUser() {
        String name = "John";
        String email = "john@gmail.com";
        String password = "123456";

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPasswordConfirmation(password);

        Cart cart = new Cart();

        when(userRepository.exists(user))
                .thenReturn(false);

        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        }).when(userRepository).create(user, cart.getId());

        userService.create(user);

        assertTrue(user.getRoles().contains(Role.ROLE_USER));
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertNotNull(user.getCart());
        assertNotNull(user.getOrders());
        assertNotNull(user.getAddresses());
        assertNotNull(user.getCreatedAt());
        verify(userRepository).exists(user);
        verify(cartService).create(cart);
        verify(userRepository).create(user, cart.getId());
        verify(userRepository).saveRoles(user.getId(), user.getRoles());
    }

    @Test
    void createNotExistingUserWithMismatchingPasswordConfirmation() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setPassword("123456");
        user.setPasswordConfirmation("1234567");

        assertThrows(IllegalStateException.class, () -> userService.create(user));
        verify(userRepository).exists(user);
        verify(cartService, never()).create(any(Cart.class));
        verify(userRepository, never()).create(any(User.class), anyLong());
        verify(userRepository, never()).saveRoles(anyLong(), anySet());
    }

    @Test
    void createExistingUser() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");

        when(userRepository.exists(user))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.create(user));
        verify(userRepository).exists(user);
        verify(userRepository, never()).create(eq(user), any());
        verify(userRepository, never()).saveRoles(any(), any());
    }

    @Test
    void addAddress() {
        Long userId = 1L;
        Address address = new Address();
        address.setStreetName("Mogilevskaya");
        address.setHouseNumber(1);

        doAnswer(invocation -> {
            Address address1 = invocation.getArgument(0);
            address1.setId(1L);
            return address1;
        }).when(addressService).create(address);

        userService.addAddress(userId, address);
        verify(addressService).create(address);
        verify(userRepository).addAddressById(userId, address.getId());
    }

    @Test
    void deleteAddressById() {
        Long userId = 1L;
        Long addressId = 1L;
        userService.deleteAddressById(userId, addressId);

        verify(userRepository).deleteAddressById(userId, addressId);
    }

    @Test
    void delete() {
        Long id = 1L;
        userService.delete(id);

        verify(userRepository).delete(id);
    }
}