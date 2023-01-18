package com.solvd.dp.web.controller;

import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Cart;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.User;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.CartService;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.service.UserService;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.user.CartDto;
import com.solvd.dp.web.dto.user.OrderDto;
import com.solvd.dp.web.dto.user.UserDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.AddressMapper;
import com.solvd.dp.web.mapper.CartMapper;
import com.solvd.dp.web.mapper.OrderMapper;
import com.solvd.dp.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final CartService cartService;
    private final OrderService orderService;

    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    @PutMapping
    public void update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userService.update(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Get')")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Delete')")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}/items/{itemId}")
    @PreAuthorize("hasPermission(#id, 'Put')")
    public void addItemById(@PathVariable Long id,
                            @PathVariable Long itemId,
                            @RequestParam(required = false) Long quantity) {
        cartService.addItemById(id, itemId, quantity);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    @PreAuthorize("hasPermission(#id, 'Delete')")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId,
                               @RequestParam(required = false) Long quantity) {
        cartService.deleteItemById(id, itemId, quantity);
    }

    @DeleteMapping("/{id}/items")
    @PreAuthorize("hasPermission(#id, 'Delete')")
    public void clearCartById(@PathVariable Long id) {
        cartService.clearById(id);
    }

    @GetMapping("/{id}/cart")
    @PreAuthorize("hasPermission(#id, 'Get')")
    public CartDto getCartById(@PathVariable Long id) {
        Cart cart = cartService.getByUserId(id);
        return cartMapper.toDto(cart);
    }

    @GetMapping("/{id}/addresses")
    @PreAuthorize("hasPermission(#id, 'Get')")
    public List<AddressDto> getAllAddresses(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByUserId(id);
        return addressMapper.toDto(addresses);
    }

    @PostMapping("/{id}/addresses")
    @PreAuthorize("hasPermission(#id, 'Post')")
    public void addAddress(@PathVariable Long id,
                           @Validated(OnCreate.class) @RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        userService.addAddress(id, address);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    @PreAuthorize("hasPermission(#id, 'Delete')")
    public void deleteAddress(@PathVariable Long id,
                              @PathVariable Long addressId) {
        userService.deleteAddressById(id, addressId);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("hasPermission(#id, 'Get')")
    public List<OrderDto> getAllOrders(@PathVariable Long id) {
        List<Order> orders = orderService.getAllByUserId(id);
        return orderMapper.toDto(orders);
    }

    @PostMapping("/{id}/orders")
    @PreAuthorize("hasPermission(#id, 'Post')")
    public OrderDto create(@PathVariable Long id,
                           @Validated(OnCreate.class) @RequestBody OrderDto dto) {
        Order orderToBeCreated = orderMapper.toEntity(dto);
        Order order = orderService.create(orderToBeCreated, id);
        return orderMapper.toDto(order);
    }

}
