package com.jnas.books_marketplace_be.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserResponseDto>> findAllUsers(
            @PageableDefault(size = 20, sort = "userName") Pageable pageable) {
        Page<UserResponseDto> users = userService.findAllUsers(pageable);
        return ResponseEntity.ok(users);

    }

    @PatchMapping("/{user-id}/archive")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> archiveUser(@PathVariable("user-id") long userId) {
        userService.archiveUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{user-id}")
    @PreAuthorize("#userId == principal.id or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateUser(
            @Valid @PathVariable("user-id") long userId,
            @RequestBody UserRequestDto userRequestDto) {

        userService.updateUser(userId, userRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{user-id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.id")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("user-id") long userId) {

        UserResponseDto user = userService.findById(userId);
        return ResponseEntity.ok(user);

    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto user = userService.createUser(userRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

}
