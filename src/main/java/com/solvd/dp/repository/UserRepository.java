package com.solvd.dp.repository;

import com.solvd.dp.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {

    Optional<User> findById(@Param("id") Long id);

    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    void update(@Param("user") User user);

    void create(@Param("user") User user, @Param("cartId") Long cartId);

    void addAddressById(@Param("userId") Long userId, @Param("addressId") Long addressId);

    void deleteAddressById(@Param("userId") Long userId, @Param("addressId") Long addressId);

    boolean exists(@Param("user") User user);

    void delete(@Param("id") Long id);

}
