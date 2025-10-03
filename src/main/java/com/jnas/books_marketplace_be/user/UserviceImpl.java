package com.jnas.books_marketplace_be.user;

import com.jnas.books_marketplace_be.role.Role;
import com.jnas.books_marketplace_be.role.RoleName;
import com.jnas.books_marketplace_be.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserviceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserRequestDto userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        Role role=roleRepository.findByName(RoleName.valueOf(userRequest.getRoleName())).orElseThrow(() -> new RuntimeException("Role not found"));
        User user = userMapper.mapToUserEntity(userRequest,role);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<UserResponseDto> findAllUsers(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(userMapper::mapToUserResponseDto)
                .toList();
    }

    @Override
    public void updateUser(Long userId, UserRequestDto userRequest) {

        User user = userRepository.findById(userId)
                 .orElseThrow(() -> new RuntimeException("user not found"));
        user.setUserName(userRequest.getUserName());
    }

    @Override
    public UserResponseDto findById(Long userId) {
       return userRepository.findById(userId)
               .map(userMapper::mapToUserResponseDto)
               .orElseThrow(()-> new RuntimeException("user not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));
    }
}
