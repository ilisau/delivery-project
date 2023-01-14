package com.solvd.dp.service;

import com.solvd.dp.domain.user.Cart;

public interface CartService {

    Cart getById(Long id);

    Cart getByUserId(Long userId);

    Cart create(Cart cart);

    void setEmptyByUserId(Long userId);

    void clearById(Long id);

    void addItemById(Long userId, Long itemId, Long quantity);

    void deleteItemById(Long userId, Long itemId, Long quantity);

    void delete(Long id);

}
