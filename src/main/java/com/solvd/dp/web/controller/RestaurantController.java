package com.solvd.dp.web.controller;

import com.solvd.dp.domain.restaurant.Restaurant;
import com.solvd.dp.domain.user.Address;
import com.solvd.dp.service.RestaurantService;
import com.solvd.dp.web.dto.OnCreate;
import com.solvd.dp.web.dto.OnUpdate;
import com.solvd.dp.web.dto.restaurant.RestaurantDto;
import com.solvd.dp.web.dto.user.AddressDto;
import com.solvd.dp.web.mapper.AddressMapper;
import com.solvd.dp.web.mapper.RestaurantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/restaurants")
@RestController
@RequiredArgsConstructor
@Validated
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDto> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();
        return restaurants.stream()
                .map(RestaurantMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping
    @Validated(OnUpdate.class)
    public void save(@Valid @RequestBody RestaurantDto dto) {
        Restaurant restaurant = RestaurantMapper.INSTANCE.toEntity(dto);
        restaurantService.save(restaurant);
    }

    @PostMapping
    @Validated(OnCreate.class)
    public RestaurantDto create(@Valid @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurantToBeCreated = RestaurantMapper.INSTANCE.toEntity(restaurantDto);
        Restaurant restaurant = restaurantService.create(restaurantToBeCreated);
        return RestaurantMapper.INSTANCE.toDto(restaurant);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void deleteItemById(@PathVariable Long id,
                               @PathVariable Long itemId) {
        restaurantService.deleteItemById(id, itemId);
    }

    @PostMapping("/{id}/addresses")
    @Validated(OnCreate.class)
    public void addAddress(@PathVariable Long id,
                           @Valid @RequestBody AddressDto addressDto) {
        Address address = AddressMapper.INSTANCE.toEntity(addressDto);
        restaurantService.addAddressById(id, address);
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public void deleteAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        restaurantService.deleteAddressById(id, addressId);
    }

    @GetMapping("/{id}")
    public RestaurantDto getById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getById(id);
        return RestaurantMapper.INSTANCE.toDto(restaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        restaurantService.delete(id);
    }

}
