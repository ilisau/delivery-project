package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.repository.CartRepository;
import com.solvd.dp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return cartRepository.getByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart bot found for this user id :: " + id));
    }

    @Override
    @Transactional
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart create(Cart cart) {
        return cartRepository.create(cart);
    }

    @Override
    @Transactional
    public void setCartToUserById(Long userId) {
        Cart cartToBeCreated = new Cart();
        Cart cart = cartRepository.create(cartToBeCreated);
        cartRepository.setCartByUserId(cart.getId(), userId);
    }

    @Override
    @Transactional
    public void clearById(Long id) {
        cartRepository.clear(id);
    }

    @Override
    @Transactional
    public void addItemById(Long id, Long itemId, long quantity) {
        cartRepository.addItemById(id, itemId, quantity);
    }

    @Override
    @Transactional
    public void deleteItemById(Long id, Long itemId, long quantity) {
        cartRepository.deleteItemById(id, itemId, quantity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cartRepository.delete(id);
    }

}
