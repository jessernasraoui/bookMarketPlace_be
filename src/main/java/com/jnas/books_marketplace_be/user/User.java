package com.jnas.books_marketplace_be.user;

import com.jnas.books_marketplace_be.book.Book;
import com.jnas.books_marketplace_be.common.AbstractEntity;
import com.jnas.books_marketplace_be.order.Order;
import com.jnas.books_marketplace_be.role.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "USERS")
public class User extends AbstractEntity implements UserDetails {


   @Column(nullable = false,unique = true)
   private String userName;
   @Column(unique = true, nullable = false)
   private String email;
   @JoinColumn(name = "role_id", nullable = false)
   @ManyToOne()
   private Role role;
   @OneToMany(mappedBy = "buyer")
   private List<Order> orders;
   @OneToMany(mappedBy = "seller")
   private List<Book> books ;
   @Column(nullable = false)
   private String password;
   @Column(nullable = false)
   private Boolean UserIsArchived=false; // update user logic to not delete user from db

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      SimpleGrantedAuthority userRole = new SimpleGrantedAuthority(role.toString());
      return  List.of(userRole);
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return UserDetails.super.isAccountNonExpired();
   }

   @Override
   public boolean isAccountNonLocked() {
      return UserDetails.super.isAccountNonLocked();
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return UserDetails.super.isCredentialsNonExpired();
   }

   @Override
   public boolean isEnabled() {
      return UserDetails.super.isEnabled();
   }
}
