package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Item;
import com.solvd.dp.domain.restaurant.ItemType;
import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.domain.user.Order;
import com.solvd.dp.domain.user.OrderStatus;
import com.solvd.dp.service.AddressService;
import com.solvd.dp.service.ItemService;
import com.solvd.dp.service.OrderService;
import com.solvd.dp.service.RestaurantService;
import com.solvd.dp.web.dto.restaurant.ItemDto;
import com.solvd.dp.web.dto.restaurant.RestaurantDto;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.user.OrderDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.AddressMapper;
import com.solvd.dp.web.mapper.ItemMapper;
import com.solvd.dp.web.mapper.OrderMapper;
import com.solvd.dp.web.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/restaurants")
@RestController
@RequiredArgsConstructor
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final AddressService addressService;
    private final ItemService itemService;
    private final OrderService orderService;

    private final AddressMapper addressMapper;
    private final RestaurantMapper restaurantMapper;
    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<RestaurantDto> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();
        return restaurantMapper.toDto(restaurants);
    }

    @PutMapping
    @PreAuthorize("canAccessRestaurant(#dto.id)")
    public void update(@Validated(OnUpdate.class) @RequestBody RestaurantDto dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurantService.update(restaurant);
    }

    @PostMapping
    @PreAuthorize("canCreateRestaurant()")
    public RestaurantDto create(@Validated(OnCreate.class) @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToBeCreated = restaurantMapper.toEntity(restaurantDto);
        Restaurant restaurant = restaurantService.create(restaurantToBeCreated);
        return restaurantMapper.toDto(restaurant);
    }

    @GetMapping("/{id}/items")
    public List<ItemDto> getAllItems(@PathVariable Long id,
                                     @RequestParam(required = false) ItemType type) {
        List<Item> items;
        if (type == null) {
            items = itemService.getAllByRestaurantId(id);
        } else {
            items = itemService.getAllByRestaurantIdAndType(id, type);
        }
        return itemMapper.toDto(items);
    }

    @PostMapping("/{id}/items")
    @PreAuthorize("canAccessRestaurant(#id)")
    public ItemDto createItem(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody ItemDto itemDto) {
        Item itemToBeCreated = itemMapper.toEntity(itemDto);
        Item item = itemService.create(itemToBeCreated, id);
        return itemMapper.toDto(item);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    @PreAuthorize("canAccessRestaurant(#id)")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId) {
        restaurantService.deleteItemById(id, itemId);
    }

    @GetMapping("/{id}/addresses")
    public List<AddressDto> getAllAddresses(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByRestaurantId(id);
        return addressMapper.toDto(addresses);
    }

    @PostMapping("/{id}/addresses")
    @PreAuthorize("canAccessRestaurant(#id)")
    public void addAddress(@PathVariable Long id,
                           @Validated(OnCreate.class) @RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        restaurantService.addAddress(id, address);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    @PreAuthorize("canAccessRestaurant(#id)")
    public void deleteAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        restaurantService.deleteAddressById(id, addressId);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("canAccessRestaurant(#id)")
    public List<OrderDto> getAllOrders(@PathVariable Long id,
                                       @RequestParam(required = false) OrderStatus status) {
        List<Order> orders;
        if (status == null) {
            orders = orderService.getAllByRestaurantId(id);
        } else {
            orders = orderService.getAllByRestaurantIdAndStatus(id, status);
        }
        return orderMapper.toDto(orders);
    }

    @GetMapping("/{id}")
    public RestaurantDto getById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getById(id);
        return restaurantMapper.toDto(restaurant);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("canAccessRestaurant(#id)")
    public void deleteById(@PathVariable Long id) {
        restaurantService.delete(id);
    }

}
