package com.example.dp.web.controller.restaurant;

import com.example.dp.service.RestaurantService;
import com.example.dp.web.dto.mapper.RestaurantMapper;
import com.example.dp.web.dto.restaurant.CreateRestaurantDto;
import com.example.dp.web.dto.restaurant.RestaurantDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.dp.web.controller.restaurant.RestaurantURLs.BASE_URL;
import static com.example.dp.web.controller.restaurant.RestaurantURLs.ID_URL;

@RequestMapping(BASE_URL)
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public RestaurantDto createRestaurant(@Valid @RequestBody CreateRestaurantDto createRestaurantDto) {
        return RestaurantMapper.INSTANCE.toDto(restaurantService.create(createRestaurantDto));
    }

    @GetMapping(ID_URL)
    public RestaurantDto getRestaurantById(@PathVariable Long id) {
        return RestaurantMapper.INSTANCE.toDto(restaurantService.getById(id));
    }

    @DeleteMapping(ID_URL)
    public void deleteRestaurantById(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }
}
