package com.example.dp.repository;

import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.web.dto.user.CreateUserDto;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    User save(User user);

    User create(CreateUserDto userDto);

    void addAddress(Long userId, Long addressId);

    void deleteAddress(Long userId, Long addressId);

    void delete(Long id);
}
