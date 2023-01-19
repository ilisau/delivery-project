package com.solvd.dp.repository;

import com.solvd.dp.domain.user.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AddressRepository {

    Optional<Address> findById(Long id);

    List<Address> getAllByUserId(Long userId);

    List<Address> getAllByRestaurantId(Long restaurantId);

    void update(Address address);

    void create(Address address);

    boolean isUserOwner(@Param("addressId") Long addressId, @Param("userId") Long userId);

    void delete(Long id);

}
