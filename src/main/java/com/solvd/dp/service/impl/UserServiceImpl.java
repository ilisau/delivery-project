package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceAlreadyExistsException;
import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.repository.UserRepository;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.CartService;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderService orderService;
    private final AddressService addressService;

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
    public User save(User user) {
        if (userRepository.exists(user)) {
            throw new ResourceAlreadyExistsException("User with this email or phone number already exists");
        }
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.exists(user)) {
            throw new ResourceAlreadyExistsException("User already exists :: " + user);
        }
        Cart cart = new Cart();
        cart.setItems(new HashMap<>());
        cartService.create(cart);
        userRepository.create(user, cart.getId());
        user.setCart(cart);
        user.setOrders(new ArrayList<>());
        user.setAddresses(new ArrayList<>());
        return user;
    }

    @Override
    @Transactional
    public void addAddress(Long userId, Address address) {
        addressService.create(address);
        userRepository.addAddress(userId, address.getId());
    }

    @Override
    @Transactional
    public void deleteAddressById(Long userId, Long addressId) {
        userRepository.deleteAddress(userId, addressId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

}
