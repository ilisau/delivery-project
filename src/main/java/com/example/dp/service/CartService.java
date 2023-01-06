package com.example.dp.service;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Cart;
import com.example.dp.web.dto.user.CartDto;
import com.example.dp.web.dto.user.CreateCartDto;

import java.util.List;

public interface CartService {

    Cart getById(Long id) throws ResourceNotFoundException;

    List<Cart> getAllByUserId(Long userId) throws ResourceNotFoundException;

    Cart getByUserId(Long userId) throws ResourceNotFoundException;

    Cart save(Cart cart);

    Cart create(CreateCartDto createCartDto);

    void deleteById(Long id);
}
