package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.service.CartService;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.user.CartDto;
import com.solvd.dp.web.mapper.CartMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/carts")
@RestController
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody CartDto cartDto) {
        Cart cart = CartMapper.INSTANCE.toEntity(cartDto);
        cartService.save(cart);
    }

    @GetMapping("/{id}")
    @Validated(OnUpdate.class)
    public CartDto getById(@PathVariable Long id) {
        Cart cart = cartService.getById(id);
        return CartMapper.INSTANCE.toDto(cart);
    }

    @DeleteMapping("/{id}")
    public void clearById(@PathVariable Long id) {
        cartService.clearById(id);
    }

    @GetMapping("/users/{id}")
    public CartDto getByUserId(@PathVariable Long id) {
        Cart cart = cartService.getByUserId(id);
        return CartMapper.INSTANCE.toDto(cart);
    }

    @PostMapping("/{id}/items/{itemId}")
    public void addItemById(@PathVariable Long id,
                            @PathVariable Long itemId,
                            @RequestParam(name = "quantity", required = false) Long quantity) {
        if (quantity == null || quantity < 1) {
            quantity = 1L;
        }
        cartService.addItemById(id, itemId, quantity);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId,
                               @RequestParam(name = "quantity", required = false) Long quantity) {
        if (quantity == null || quantity < 1) {
            quantity = 1L;
        }
        cartService.deleteItemById(id, itemId, quantity);
    }

}
