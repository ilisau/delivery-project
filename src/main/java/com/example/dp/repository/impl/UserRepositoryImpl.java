package com.example.dp.repository.impl;

import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByCart(Cart cart) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
