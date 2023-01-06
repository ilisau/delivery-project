package com.example.dp.service.impl;

import com.example.dp.domain.exception.ResourceNotFoundException;
import com.example.dp.domain.user.User;
import com.example.dp.repository.UserRepository;
import com.example.dp.service.UserService;
import com.example.dp.web.dto.mapper.UserMapper;
import com.example.dp.web.dto.user.CreateUserDto;
import com.example.dp.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
    }

    @Override
    public User getByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found for this email :: " + email));
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) throws ResourceNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ResourceNotFoundException("User not found for this phone number :: " + phoneNumber));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(CreateUserDto userDto) {
        //TODO set cart id
        return userRepository.create(userDto);
    }

    @Override
    public void addAddress(Long userId, Long addressId) {
        userRepository.addAddress(userId, addressId);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        userRepository.deleteAddress(userId, addressId);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }
}
