package com.jnas.books_marketplace_be.user;

import com.jnas.books_marketplace_be.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUserEntity(UserRequestDto userRequest, Role role) {
        return User.builder()
                .email(userRequest.getEmail())
                .userName(userRequest.getUserName())
                .role(role)
                .password(passwordEncoder.encode( userRequest.getPassword()))
                .build();

    }

    public UserResponseDto mapToUserResponseDto(User user) {
        return  UserResponseDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
