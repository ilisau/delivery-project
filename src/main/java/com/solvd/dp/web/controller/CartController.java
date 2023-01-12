package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.service.CartService;
import com.solvd.dp.web.dto.user.CartDto;
import com.solvd.dp.web.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/carts")
@RestController
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/{id}")
    public CartDto getById(@PathVariable Long id) {
        Cart cart = cartService.getById(id);
        return cartMapper.toDto(cart);
    }

    @DeleteMapping("/{id}")
    public void clearById(@PathVariable Long id) {
        cartService.clearById(id);
    }

    @GetMapping("/users/{id}")
    public CartDto getByUserId(@PathVariable Long id) {
        Cart cart = cartService.getByUserId(id);
        return cartMapper.toDto(cart);
    }

    @PutMapping("/{id}/items/{itemId}")
    public void addItemById(@PathVariable Long id,
                            @PathVariable Long itemId,
                            @RequestParam(required = false) Long quantity) {
        cartService.addItemById(id, itemId, quantity);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId,
                               @RequestParam(required = false) Long quantity) {
        cartService.deleteItemById(id, itemId, quantity);
    }

}
