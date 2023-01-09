package com.example.dp.service;

import com.example.dp.domain.user.Cart;
import com.example.dp.web.dto.user.CreateCartDto;

public interface CartService {

    Cart getById(Long id);

    Cart getByUserId(Long userId);

    Cart save(Cart cart);

    Cart create(CreateCartDto createCartDto);

    void clearById(Long id);

    void addItemById(Long id, Long itemId, long quantity);

    void deleteItemById(Long id, Long itemId, long quantity);

    void deleteById(Long id);
}
