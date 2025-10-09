package com.jnas.books_marketplace_be.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService extends UserDetailsService {
    Page<UserResponseDto> findAllUsers(Pageable pageable);
    UserResponseDto createUser(UserRequestDto userRequest);
    UserResponseDto updateUser(Long userId, UserRequestDto userRequest);
    UserResponseDto findById(Long userId);
    void archiveUser(Long userId);
}
