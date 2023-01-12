package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.service.RestaurantService;
import com.solvd.dp.web.dto.restaurant.RestaurantDto;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.dto.validation.OnCreate;
import com.solvd.dp.web.dto.validation.OnUpdate;
import com.solvd.dp.web.mapper.AddressMapper;
import com.solvd.dp.web.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/restaurants")
@RestController
@RequiredArgsConstructor
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final AddressMapper addressMapper;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public List<RestaurantDto> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();
        return restaurantMapper.toDto(restaurants);
    }

    @PutMapping
    public void save(@Validated(OnUpdate.class) @RequestBody RestaurantDto dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurantService.save(restaurant);
    }

    @PostMapping
    public RestaurantDto create(@Validated(OnCreate.class) @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToBeCreated = restaurantMapper.toEntity(restaurantDto);
        Restaurant restaurant = restaurantService.create(restaurantToBeCreated);
        return restaurantMapper.toDto(restaurant);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId) {
        restaurantService.deleteItemById(id, itemId);
    }

    @PostMapping("/{id}/addresses")
    public void addAddress(@PathVariable Long id,
                           @Validated(OnCreate.class) @RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        restaurantService.addAddress(id, address);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public void deleteAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        restaurantService.deleteAddressById(id, addressId);
    }

    @GetMapping("/{id}")
    public RestaurantDto getById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getById(id);
        return restaurantMapper.toDto(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        restaurantService.delete(id);
    }

}
