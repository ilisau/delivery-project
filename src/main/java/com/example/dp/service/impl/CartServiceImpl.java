package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.Cart;
import com.example.dp.repository.CartRepository;
import com.example.dp.service.CartService;
import com.example.dp.service.ItemService;
import com.example.dp.web.dto.user.CreateCartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemService itemService;

    @Override
    public Cart getById(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for this id :: " + id));
        cart.setItems(itemService.getAllByCartId(id));
        return cart;
    }

    @Override
    public Cart getByUserId(Long id) throws ResourceNotFoundException {
        Cart cart = cartRepository.getByUserId(id);
        cart.setItems(itemService.getAllByCartId(cart.getId()));
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
