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
import com.solvd.dp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setOrders(orderService.getAllByUserId(id));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this email :: " + email));
        user.setOrders(orderService.getAllByUserId(user.getId()));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this phone number :: " + phoneNumber));
        user.setOrders(orderService.getAllByUserId(user.getId()));
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        if (userRepository.exists(user)) {
            throw new ResourceAlreadyExistsException("User with this email or phone number already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.exists(user)) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation are not equal");
        }
        Cart cart = new Cart();
        cartService.create(cart);
        user.setCreatedAt(LocalDateTime.now());
        user.setCart(cart);
        user.setRoles(Set.of(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user, cart.getId());
        userRepository.saveRoles(user.getId(), user.getRoles());
        user.setOrders(new ArrayList<>());
        user.setAddresses(new ArrayList<>());
        return user;
    }

    @Override
    @Transactional
    public void addAddress(Long userId, Address address) {
        addressService.create(address);
        userRepository.addAddressById(userId, address.getId());
    }

    @Override
    @Transactional
    public void deleteAddressById(Long userId, Long addressId) {
        userRepository.deleteAddressById(userId, addressId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
