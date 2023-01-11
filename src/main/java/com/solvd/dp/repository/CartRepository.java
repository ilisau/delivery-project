package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Cart;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findById(Long id);

    Optional<Cart> getByUserId(Long id);

    Cart save(Cart cart);

    Cart create(Cart cart);

    void clear(Long id);

    void setCartByUserId(Long id, Long userId);

    void addItemById(Long id, Long itemId, long quantity);

    void deleteItemById(Long id, Long itemId, long quantity);

    void delete(Long id);

}