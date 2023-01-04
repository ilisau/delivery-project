package com.example.dp.repository;

import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    List<Cart> getAllByUser(User user);

    Cart save(Cart cart);

    void delete(Long id);
}
