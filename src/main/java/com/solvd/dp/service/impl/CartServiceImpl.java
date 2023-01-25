package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.repository.CartRepository;
import com.solvd.dp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public Cart getById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getByUserId(Long id) {
        return cartRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart bot found for this user id :: " + id));
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        cart.setItems(new HashMap<>());
        cartRepository.create(cart);
        return cart;
    }

    @Override
    @Transactional
    public void setEmptyByUserId(Long userId) {
        Cart cart = new Cart();
        cart.setItems(new HashMap<>());
        cartRepository.create(cart);
        cartRepository.setByUserId(cart.getId(), userId);
    }

    @Override
    @Transactional
    public void clearById(Long id) {
        cartRepository.clear(id);
    }

    @Override
    @Transactional
    public void addItemById(Long userId, Long itemId, Long quantity) {
        if (quantity == null) {
            quantity = 1L;
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Amount of items can't be negative");
        }
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this user id :: " + userId));
        cartRepository.addItemById(cart.getId(), itemId, quantity);
    }

    @Override
    @Transactional
    public void deleteItemById(Long userId, Long itemId, Long quantity) {
        if (quantity == null) {
            quantity = 1L;
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Amount of items can't be negative");
        }
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this user id :: " + userId));
        cartRepository.deleteItemById(cart.getId(), itemId, quantity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cartRepository.delete(id);
    }

}
