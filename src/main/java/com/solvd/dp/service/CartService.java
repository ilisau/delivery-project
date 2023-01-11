package com.solvd.dp.service;

import com.solvd.dp.domain.user.Cart;

public interface CartService {

    Cart getById(Long id);

    Cart getByUserId(Long userId);

    Cart save(Cart cart);

    Cart create(Cart cart);

    void setEmptyByUserId(Long userId);

    void clearById(Long id);

    void addItemById(Long cartId, Long itemId, long quantity);

    void deleteItemById(Long cartId, Long itemId, long quantity);

    void delete(Long id);

}
