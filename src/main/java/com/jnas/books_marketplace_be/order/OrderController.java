package com.jnas.books_marketplace_be.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{userId}")
    @PreAuthorize("#userId== principal.id")

    public ResponseEntity<Page<OrderResponseDTO>> getOrders(Pageable pageable, @PathVariable long  userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId, pageable));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO order = orderService.addOrder(orderRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();
        return ResponseEntity.created(location).body(order);    }

}
