package com.example.dp.repository;

import com.example.dp.domain.user.Cart;
import com.example.dp.domain.user.User;
import com.example.dp.web.dto.user.CreateCartDto;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    List<Cart> getAllByUser(User user);

    Cart getByUser(User user);

    Cart save(Cart cart);

    Cart create(CreateCartDto createCartDto);

    void delete(Long id);
}