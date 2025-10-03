package com.jnas.books_marketplace_be.role;

import com.jnas.books_marketplace_be.common.AbstractEntity;
import com.jnas.books_marketplace_be.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "ROLES")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true)
    private RoleName name;
    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
