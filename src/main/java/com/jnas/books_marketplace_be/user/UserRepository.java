package com.jnas.books_marketplace_be.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String Email);

    Optional<User> findByEmailAndUserIsArchivedFalse(String email);

    Optional<User> findByIdAndUserIsArchivedFalse(Long id);

    Page<User> findByUserIsArchivedFalse(Pageable pageable);
}
