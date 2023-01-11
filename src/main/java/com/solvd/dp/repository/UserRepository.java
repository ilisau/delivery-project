package com.solvd.dp.repository;

import com.solvd.dp.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    User save(User user);

    User create(User user, Long cartId);

    void addAddress(Long userId, Long addressId);

    void deleteAddress(Long userId, Long addressId);

    boolean exists(User user);

    void delete(Long id);

}
