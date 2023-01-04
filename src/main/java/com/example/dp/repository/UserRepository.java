package com.example.dp.repository;

import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByCart(Cart cart);

    User save(User user);

    void delete(Long id);
}
