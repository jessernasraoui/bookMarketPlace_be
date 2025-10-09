package com.jnas.books_marketplace_be.user;

import com.jnas.books_marketplace_be.role.Role;
import com.jnas.books_marketplace_be.role.RoleName;
import com.jnas.books_marketplace_be.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        // The default role is client
        Role role = roleRepository.findByName(RoleName.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Role ROLE_CLIENT not found"));


        User user = userMapper.mapToUserEntity(userRequest, role);
        userRepository.save(user);

        return userMapper.mapToUserResponseDto(user);
    }

    @Override
    public Page<UserResponseDto> findAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findByUserIsArchivedFalse(pageable);

        List<UserResponseDto> content = usersPage.getContent()
                .stream()
                .map(userMapper::mapToUserResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, usersPage.getTotalElements());
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequest) {
        User user = userRepository.findByIdAndUserIsArchivedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());

        userRepository.save(user);
        return userMapper.mapToUserResponseDto(user);
    }

    @Override
    public UserResponseDto findById(Long userId) {
        User user = userRepository.findByIdAndUserIsArchivedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.mapToUserResponseDto(user);
    }

    @Override
    public void archiveUser(Long userId) {
        User user = userRepository.findByIdAndUserIsArchivedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setUserIsArchived(true);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmailAndUserIsArchivedFalse(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));
    }
}