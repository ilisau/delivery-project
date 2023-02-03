package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Cart cart = new Cart();
        cart.setId(id);

        when(cartRepository.findById(id))
                .thenReturn(Optional.of(cart));

        assertEquals(cart, cartService.getById(id));
        verify(cartRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        Cart cart = new Cart();
        cart.setId(id);

        when(cartRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getById(id));
        verify(cartRepository).findById(id);
    }

    @Test
    void getByExistingUserId() {
        Long userId = 1L;
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        assertEquals(cart, cartService.getByUserId(userId));
        verify(cartRepository).findByUserId(userId);
    }

    @Test
    void getByNotExistingUserId() {
        Long userId = 1L;
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getByUserId(userId));
        verify(cartRepository).findByUserId(userId);
    }

    @Test
    void create() {
        Cart cart = new Cart();

        doAnswer(invocation -> {
            Cart cart1 = invocation.getArgument(0);
            cart1.setId(1L);
            return cart1;
        }).when(cartRepository).create(cart);

        assertEquals(cart, cartService.create(cart));
        assertNotNull(cart.getItems());
        verify(cartRepository).create(cart);
    }

    @Test
    void setEmptyByUserId() {
        Long userId = 1L;
        Long cartId = 1L;
        Cart cart = new Cart();

        doAnswer(invocation -> {
            Cart cart1 = invocation.getArgument(0);
            cart1.setId(cartId);
            return cart1;
        }).when(cartRepository).create(cart);

        cartService.setEmptyByUserId(userId);

        assertNotNull(cart.getItems());
        verify(cartRepository).create(any());
        verify(cartRepository).setByUserId(cartId, userId);
    }

    @Test
    void clearById() {
        Long id = 1L;
        cartService.clearById(id);
        verify(cartRepository).clear(id);
    }

    @Test
    void addItemByIdWithNullQuantity() {
        Long userId = 1L;
        Long cartId = 1L;
        Long itemId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new HashMap<>());

        Item item = new Item();
        item.setId(itemId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        cartService.addItemById(userId, itemId, null);

        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).addItemById(cartId, itemId, 1L);
    }

    @Test
    void addItemByIdWithPositiveQuantity() {
        Long userId = 1L;
        Long cartId = 1L;
        Long itemId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new HashMap<>());

        Item item = new Item();
        item.setId(itemId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        cartService.addItemById(userId, itemId, 5L);

        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).addItemById(cartId, itemId, 5L);
    }

    @Test
    void addItemByIdToNotExistingUser() {
        Long userId = 1L;
        Long cartId = 1L;
        Long itemId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new HashMap<>());

        Item item = new Item();
        item.setId(itemId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.addItemById(userId, itemId, 5L));
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository, never()).addItemById(cartId, itemId, 5L);
    }

    @Test
    void addItemByIdWithNegativeQuantity() {
        Long userId = 1L;
        Long cartId = 1L;
        Long itemId = 1L;

        assertThrows(IllegalArgumentException.class, () -> cartService.addItemById(userId, itemId, -5L));
        verify(cartRepository, never()).findByUserId(userId);
        verify(cartRepository, never()).addItemById(cartId, itemId, 5L);
    }

    @Test
    void deleteItemByIdWithNullQuantity() {
        Long userId = 1L;
        Long cartId = 1L;
        Long itemId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new HashMap<>());

        Item item = new Item();
        item.setId(itemId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        cartService.deleteItemById(userId, itemId, null);

        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).deleteItemById(cartId, itemId, 1L);
    }

    @Test
    void deleteItemByIdWithPositiveQuantity() {
        Long userId = 1L;
        Long cartId = 1L;
        Long itemId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new HashMap<>());

        Item item = new Item();
        item.setId(itemId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        cartService.deleteItemById(userId, itemId, 5L);

        verify(cartRepository).findByUserId(userId);
        verify(cartRepository).deleteItemById(cartId, itemId, 5L);
    }

    @Test
    void deleteItemByIdToNotExistingUser() {
        Long userId = 1L;
        Long itemId = 1L;
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(new HashMap<>());

        Item item = new Item();
        item.setId(itemId);

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.deleteItemById(userId, itemId, 5L));
        verify(cartRepository).findByUserId(userId);
        verify(cartRepository, never()).deleteItemById(cartId, itemId, 5L);
    }

    @Test
    void deleteItemByIdWithNegativeQuantity() {
        Long userId = 1L;
        Long itemId = 1L;

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteItemById(userId, itemId, -5L));
        verify(cartRepository, never()).findByUserId(userId);
        verify(cartRepository, never()).deleteItemById(any(), eq(userId), eq(5L));
    }

    @Test
    void delete() {
        Long id = 1L;
        cartService.delete(id);

        verify(cartRepository).delete(id);
    }
}