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
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getByExistingEmail() {
        String email = "john@gmail.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        assertEquals(user, userService.getByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getByNotExistingEmail() {
        String email = "john@gmail.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getByExistingPhoneNumber() {
        String phoneNumber = "123456789";
        User user = new User();
        user.setPhoneNumber(phoneNumber);

        when(userRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.of(user));

        assertEquals(user, userService.getByPhoneNumber(phoneNumber));
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
    }

    @Test
    void getByNotExistingPhoneNumber() {
        String phoneNumber = "123456789";

        when(userRepository.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByPhoneNumber(phoneNumber));
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
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
        verify(userRepository, times(1)).exists(user);
        verify(userRepository, times(0)).update(user);
    }

    @Test
    void updateNotExistingUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setPassword("123456");

        when(userRepository.exists(user))
                .thenReturn(false);

        doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setName("John");
            u.setEmail("john@gmail.com");
            return u;
        }).when(userRepository).update(user);

        userService.update(user);

        assertEquals("John", user.getName());
        assertEquals("john@gmail.com", user.getEmail());
        assertTrue(passwordEncoder.matches("123456", user.getPassword()));
        verify(userRepository, times(1)).exists(user);
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void createNotExistingUser() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setPassword("123456");
        user.setPasswordConfirmation("123456");

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
        assertTrue(passwordEncoder.matches("123456", user.getPassword()));
        assertNotNull(user.getCart());
        assertNotNull(user.getOrders());
        assertNotNull(user.getAddresses());
        assertNotNull(user.getCreatedAt());
        verify(userRepository, times(1)).exists(user);
        verify(cartService, times(1)).create(cart);
        verify(userRepository, times(1)).create(user, cart.getId());
        verify(userRepository, times(1)).saveRoles(user.getId(), user.getRoles());
    }

    @Test
    void createNotExistingUserWithMismatchingPasswordConfirmation() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setPassword("123456");
        user.setPasswordConfirmation("1234567");

        assertThrows(IllegalStateException.class, () -> userService.create(user));
        verify(userRepository, times(1)).exists(user);
        verify(cartService, times(0)).create(any(Cart.class));
        verify(userRepository, times(0)).create(any(User.class), anyLong());
        verify(userRepository, times(0)).saveRoles(anyLong(), anySet());
    }

    @Test
    void createExistingUser() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");

        when(userRepository.exists(user))
                .thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.create(user));
        verify(userRepository, times(1)).exists(user);
        verify(userRepository, times(0)).create(eq(user), any());
        verify(userRepository, times(0)).saveRoles(any(), any());
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
        verify(addressService, times(1)).create(address);
        verify(userRepository, times(1)).addAddressById(userId, address.getId());
    }

    @Test
    void deleteAddressById() {
        Long userId = 1L;
        Long addressId = 1L;
        userService.deleteAddressById(userId, addressId);

        verify(userRepository, times(1)).deleteAddressById(userId, addressId);
    }

    @Test
    void delete() {
        Long id = 1L;
        userService.delete(id);

        verify(userRepository, times(1)).delete(id);
    }
}