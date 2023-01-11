package com.solvd.dp.service;

import com.solvd.dp.domain.user.Cart;

public interface CartService {

    Cart getById(Long id);

    Cart getByUserId(Long userId);

    Cart save(Cart cart);

    Cart create(Cart cart);

    void setCartToUserById(Long userId);

    void clearById(Long id);

    void addItemById(Long id, Long itemId, long quantity);

    void deleteItemById(Long id, Long itemId, long quantity);

    void deleteById(Long id);

}
