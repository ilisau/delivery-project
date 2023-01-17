package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AddressRepository {

    Optional<Address> findById(@Param("id") Long id);

    List<Address> getAllByUserId(@Param("userId") Long userId);

    List<Address> getAllByRestaurantId(@Param("restaurantId") Long restaurantId);

    void update(@Param("address") Address address);

    void create(@Param("address") Address address);

    void delete(@Param("id") Long id);

}
