package com.solvd.dp.service.impl;

import com.solvd.dp.domain.exception.ResourceNotFoundException;
import com.solvd.dp.domain.restaurant.CartItem;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.repository.OrderRepository;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.CartService;
import com.solvd.dp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Order order = new Order();
        order.setId(id);

        when(orderRepository.findById(id))
                .thenReturn(Optional.of(order));

        assertEquals(order, orderService.getById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;

        when(orderRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void getAllByUserId() {
        Long id = 1L;
        orderService.getAllByUserId(id);

        verify(orderRepository, times(1)).getAllByUserId(id);
    }

    @Test
    void getAllByAddressId() {
        Long id = 1L;
        orderService.getAllByAddressId(id);

        verify(orderRepository, times(1)).getAllByAddressId(id);
    }

    @Test
    void getAllByCourierId() {
        Long id = 1L;
        orderService.getAllByCourierId(id);

        verify(orderRepository, times(1)).getAllByCourierId(id);
    }

    @Test
    void getAllByRestaurantId() {
        Long id = 1L;
        orderService.getAllByRestaurantId(id);

        verify(orderRepository, times(1)).getAllByRestaurantId(id);
    }

    @Test
    void getAllByRestaurantIdAndStatus() {
        Long id = 1L;
        OrderStatus status = OrderStatus.PREPARING;
        orderService.getAllByRestaurantIdAndStatus(id, status);

        verify(orderRepository, times(1)).getAllByRestaurantIdAndStatus(id, status);
    }

    @Test
    void update() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PREPARING);

        doAnswer(invocation -> {
            Order order1 = invocation.getArgument(0);
            order1.setStatus(OrderStatus.PREPARING);
            return order1;
        }).when(orderRepository).update(order);

        orderService.update(order);
        assertEquals(OrderStatus.PREPARING, order.getStatus());
        verify(orderRepository, times(1)).update(order);
    }

    @Test
    void createWithEmptyCart() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.setId(1L);

        when(cartService.getByUserId(1L))
                .thenReturn(cart);

        assertThrows(IllegalStateException.class, () -> orderService.create(order, 1L));
        verify(cartService, times(1)).getByUserId(1L);
        verify(orderRepository, times(0)).create(order);
    }

    @Test
    void createWithNotEmptyCartAndExistingAddress() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.setId(1L);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, "Burger", "Delicious", new BigDecimal("1.5"), ItemType.BURGER, true, 1L));
        cartItems.add(new CartItem(2L, "Soup", "So Delicious", new BigDecimal("2.5"), ItemType.SOUP, true, 1L));
        cart.setCartItems(cartItems);
        Address address = new Address();
        address.setId(1L);
        order.setAddress(address);

        when(cartService.getByUserId(1L))
                .thenReturn(cart);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        doAnswer(invocation -> {
            Order order1 = invocation.getArgument(0);
            order1.setId(1L);
            return order1;
        }).when(orderRepository).create(order);

        orderService.create(order, 1L);

        assertEquals(order.getStatus(), OrderStatus.ORDERED);
        assertNotNull(order.getCreatedAt());
        verify(cartService, times(1)).getByUserId(1L);
        verify(orderRepository, times(1)).create(order);
        verify(orderRepository, times(1)).addOrderById(1L, 1L);
        verify(cartService, times(1)).setEmptyByUserId(1L);
        verify(orderRepository, times(1)).findById(1L);
        verify(addressService, times(0)).create(address);
        verify(userService, times(0)).addAddress(1L, address);
    }

    @Test
    void createWithNotEmptyCartAndNotExistingAddress() {
        Order order = new Order();
        Cart cart = new Cart();
        cart.setId(1L);
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1L, "Burger", "Delicious", new BigDecimal("1.5"), ItemType.BURGER, true, 1L));
        cartItems.add(new CartItem(2L, "Soup", "So Delicious", new BigDecimal("2.5"), ItemType.SOUP, true, 1L));
        cart.setCartItems(cartItems);
        Address address = new Address();
        address.setStreetName("Mogilevskaya");
        address.setHouseNumber(1);
        order.setAddress(address);

        when(cartService.getByUserId(1L))
                .thenReturn(cart);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        doAnswer(invocation -> {
            Address address1 = invocation.getArgument(0);
            address1.setId(1L);
            return address1;
        }).when(addressService).create(address);

        doAnswer(invocation -> {
            Order order1 = invocation.getArgument(0);
            order1.setId(1L);
            return order1;
        }).when(orderRepository).create(order);

        orderService.create(order, 1L);

        assertEquals(order.getStatus(), OrderStatus.ORDERED);
        assertNotNull(order.getCreatedAt());
        verify(cartService, times(1)).getByUserId(1L);
        verify(orderRepository, times(1)).create(order);
        verify(orderRepository, times(1)).addOrderById(1L, 1L);
        verify(cartService, times(1)).setEmptyByUserId(1L);
        verify(orderRepository, times(1)).findById(1L);
        verify(addressService, times(1)).create(address);
        verify(userService, times(1)).addAddress(1L, address);
    }

    @Test
    void isOrderAssigned() {
        Long id = 1L;
        orderService.isOrderAssigned(id);

        verify(orderRepository, times(1)).isOrderAssigned(id);
    }

    @Test
    void assignOrder() {
        Long orderId = 1L;
        Long courierId = 1L;
        orderService.assignOrder(orderId, courierId);

        verify(orderRepository, times(1)).assignOrder(orderId, courierId);
    }

    @Test
    void updateStatus() {
        Long id = 1L;
        OrderStatus status = OrderStatus.PREPARING;
        orderService.updateStatus(id, status);

        verify(orderRepository, times(1)).updateStatus(id, status);
    }

    @Test
    void updateDeliveredStatus() {
        Long id = 1L;
        OrderStatus status = OrderStatus.DELIVERED;
        orderService.updateStatus(id, status);

        verify(orderRepository, times(1)).updateStatus(id, status);
        verify(orderRepository, times(1)).setDelivered(id);
    }

    @Test
    void isUserOwner() {
        Long orderId = 1L;
        Long userId = 1L;
        orderService.isUserOwner(orderId, userId);

        verify(orderRepository, times(1)).isUserOwner(orderId, userId);
    }

    @Test
    void isCourierOwner() {
        Long orderId = 1L;
        Long courierId = 1L;
        orderService.isCourierOwner(orderId, courierId);

        verify(orderRepository, times(1)).isCourierOwner(orderId, courierId);
    }

    @Test
    void isEmployeeOwner() {
        Long orderId = 1L;
        Long employeeId = 1L;
        orderService.isEmployeeOwner(orderId, employeeId);

        verify(orderRepository, times(1)).isEmployeeOwner(orderId, employeeId);
    }

    @Test
    void delete() {
        Long id = 1L;
        orderService.delete(id);

        verify(orderRepository, times(1)).delete(id);
    }
}