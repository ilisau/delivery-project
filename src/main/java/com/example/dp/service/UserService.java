package com.example.dp.service;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.User;
import com.example.dp.web.dto.user.CreateUserDto;
import com.example.dp.web.dto.user.UserDto;

public interface UserService {

    User getById(Long id) throws ResourceNotFoundException;

    User getByEmail(String email) throws ResourceNotFoundException;

    User getByPhoneNumber(String phoneNumber) throws ResourceNotFoundException;

    User save(User user);

    User create(CreateUserDto userDto);

    void addAddress(Long userId, Long addressId);

    void deleteAddress(Long userId, Long addressId);

    void deleteById(Long id);
}