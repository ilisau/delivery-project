package com.example.dp.repository.impl;

import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartRepositoryImpl implements CartRepository {

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Cart> getAllByUser(User user) {
        return null;
    }

    @Override
    public Cart save(Cart cartDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
