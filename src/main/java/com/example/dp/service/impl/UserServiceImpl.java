package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.repository.UserRepository;
import com.example.dp.service.AddressService;
import com.example.dp.service.CartService;
import com.example.dp.service.OrderService;
import com.example.dp.service.UserService;
import com.example.dp.web.dto.user.CreateCartDto;
import com.example.dp.web.dto.user.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final AddressService addressService;
    private final OrderService orderService;

    @Override
    public User getById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setAddresses(addressService.getAllByUserId(id));
        user.setCart(cartService.getByUserId(id));
        user.setOrders(orderService.getAllByUserId(id));
        return user;
    }

    @Override
    public User getByEmail(String email) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this email :: " + email));
        user.setAddresses(addressService.getAllByUserId(user.getId()));
        user.setCart(cartService.getByUserId(user.getId()));
        user.setOrders(orderService.getAllByUserId(user.getId()));
        return user;
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) throws ResourceNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this phone number :: " + phoneNumber));
        user.setAddresses(addressService.getAllByUserId(user.getId()));
        user.setCart(cartService.getByUserId(user.getId()));
        user.setOrders(orderService.getAllByUserId(user.getId()));
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(CreateUserDto userDto) {
        User user = userRepository.create(userDto);
        user.setCart(cartService.create(new CreateCartDto(user.getId())));
        return user;
    }

    @Override
    public void addAddress(Long userId, Long addressId) {
        userRepository.addAddress(userId, addressId);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        userRepository.deleteAddress(userId, addressId);
    }

    @Override
    public void addOrderById(Long userId, Long orderId) {
        userRepository.addOrderById(userId, orderId);
    }

    @Override
    public void deleteOrderById(Long userId, Long orderId) {
        userRepository.deleteOrderById(userId, orderId);
    }

    @Override
    public void setCartById(Long userId) {
        Cart cart = cartService.create(new CreateCartDto(userId));
        userRepository.setCartById(userId, cart.getId());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }
}
