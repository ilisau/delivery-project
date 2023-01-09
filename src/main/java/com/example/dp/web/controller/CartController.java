package com.example.dp.web.controller;

import com.example.dp.domain.user.Cart;
import com.example.dp.service.CartService;
import com.example.dp.web.dto.user.CartDto;
import com.example.dp.web.mapper.CartMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/carts")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping
    public void save(@Valid @RequestBody CartDto cartDto) {
        Cart cart = CartMapper.INSTANCE.toEntity(cartDto);
        cartService.save(cart);
    }

    @GetMapping("/{id}")
    public CartDto getById(@PathVariable Long id) {
        Cart cart = cartService.getById(id);
        return CartMapper.INSTANCE.toDto(cart);
    }

    @PostMapping("/{id}/clear")
    public void clearById(@PathVariable Long id) {
        cartService.clearById(id);
    }

    @GetMapping("/by-user/{id}")
    public CartDto getByUserId(@PathVariable Long id) {
        Cart cart = cartService.getByUserId(id);
        return CartMapper.INSTANCE.toDto(cart);
    }

    @PostMapping("/{id}/add-item/{itemId}")
    public void addItemById(@PathVariable Long id,
                            @PathVariable Long itemId,
                            @RequestParam(name = "quantity", required = false) Long quantity) {
        if (quantity == null || quantity < 1) {
            quantity = 1L;
        }
        cartService.addItemById(id, itemId, quantity);
    }

    @PostMapping("/{id}/delete-item/{itemId}")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId,
                               @RequestParam(name = "quantity", required = false) Long quantity) {
        if (quantity == null || quantity < 1) {
            quantity = 1L;
        }
        cartService.deleteItemById(id, itemId, quantity);
    }
}
