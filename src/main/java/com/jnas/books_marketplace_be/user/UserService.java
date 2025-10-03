package com.jnas.books_marketplace_be.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends UserDetailsService {
    User createUser(UserRequestDto user);
    void updateUser(Long userId, UserRequestDto user);
    List<UserResponseDto> findAllUsers(int page, int size);
    UserResponseDto findById(Long userId);
}
