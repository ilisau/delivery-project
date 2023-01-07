package com.example.dp.repository;

import com.example.dp.domain.user.Cart;
import com.example.dp.web.dto.user.CreateCartDto;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    Cart getByUserId(Long id);

    Cart save(Cart cart);

    Cart create(CreateCartDto createCartDto);

    void delete(Long id);
}