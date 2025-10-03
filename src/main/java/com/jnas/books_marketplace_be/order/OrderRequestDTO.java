package com.jnas.books_marketplace_be.order;

import com.jnas.books_marketplace_be.order_details.OrderDetails;
import com.jnas.books_marketplace_be.user.User;
import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDTO(
        long bookId,
         BigDecimal price,
         int quantity,
         User buyer,
         List<OrderDetails> orderDetails
) {
}
