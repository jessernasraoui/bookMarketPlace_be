package com.jnas.books_marketplace_be.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
   Page<Order> findByBuyerId(Long userId, Pageable pageable);

}

