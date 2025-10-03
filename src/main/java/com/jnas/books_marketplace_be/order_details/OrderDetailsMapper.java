package com.jnas.books_marketplace_be.order_details;

public class OrderDetailsMapper {

    public static OrderDetailsDTO mapOrderDetailsToOrderDetailsDTo(OrderDetails orderDetails) {
        return new OrderDetailsDTO(
                orderDetails.getBook().getTitle(),
                orderDetails.getQuantity(),
                orderDetails.getTotalPrice()
        );
    }
}
