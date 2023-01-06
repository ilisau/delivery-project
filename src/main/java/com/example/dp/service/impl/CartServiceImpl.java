package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Cart;
import com.example.dp.repository.CartRepository;
import com.example.dp.service.CartService;
import com.example.dp.service.UserService;
import com.example.dp.web.dto.user.CartDto;
import com.example.dp.web.dto.user.CreateCartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;

    @Override
    public Cart getById(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
        //TODO set items and user
        return cart;
    }

    @Override
    public List<Cart> getAllByUserId(Long userId) throws ResourceNotFoundException {
        List<Cart> carts = cartRepository.getAllByUser(userService.getById(userId));
        //TODO set items and user
        return carts;
    }

    @Override
    public Cart getByUserId(Long userId) throws ResourceNotFoundException {
        Cart cart = cartRepository.getByUser(userService.getById(userId));
        //TODO set items and user
        return cart;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart create(CreateCartDto createCartDto) {
        return cartRepository.create(createCartDto);
    }

    @Override
    public void deleteById(Long id) {
        cartRepository.delete(id);
    }
}
