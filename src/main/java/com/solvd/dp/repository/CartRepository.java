package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Cart;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    Optional<Cart> findByUserId(Long userId);

    Cart save(Cart cart);

    Cart create(Cart cart);

    void clear(Long id);

    void setByUserId(Long cartId, Long userId);

    void addItemById(Long cartId, Long itemId, long quantity);

    void deleteItemById(Long cartId, Long itemId, long quantity);

    void delete(Long id);

}