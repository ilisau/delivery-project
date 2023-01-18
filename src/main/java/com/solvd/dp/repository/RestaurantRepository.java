package com.solvd.dp.repository;

import com.solvd.dp.domain.restaurant.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RestaurantRepository {

    List<Restaurant> getAll();

    Optional<Restaurant> findById(@Param("id") Long id);

    Optional<Restaurant> findByName(@Param("name") String name);

    void update(@Param("restaurant") Restaurant restaurant);

    void create(@Param("restaurant") Restaurant restaurant);

    void addEmployeeById(@Param("restaurantId") Long restaurantId, @Param("employeeId") Long employeeId);

    void deleteEmployeeById(@Param("restaurantId") Long restaurantId, @Param("employeeId") Long employeeId);

    void addItemById(@Param("restaurantId") Long restaurantId, @Param("itemId") Long itemId);

    void deleteItemById(@Param("restaurantId") Long restaurantId, @Param("itemId") Long itemId);

    void addAddressById(@Param("restaurantId") Long restaurantId, @Param("addressId") Long addressId);

    void deleteAddressById(@Param("restaurantId") Long restaurantId, @Param("addressId") Long addressId);

    boolean exists(@Param("restaurant") Restaurant restaurant);

    boolean employeeExists(@Param("restaurantId") Long restaurantId, @Param("employeeId") Long employeeId);

    void delete(@Param("id") Long id);

}
