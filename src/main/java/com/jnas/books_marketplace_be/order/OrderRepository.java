package com.jnas.books_marketplace_be.order;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

   @Query("""
       SELECT o
       FROM Order o
       WHERE o.buyer.id = :userId
         AND o.buyer.UserIsArchived = false
       """)
   Page<Order> findOrdersByUserIdAndNotArchived(@Param("userId") Long userId, Pageable pageable);


}

