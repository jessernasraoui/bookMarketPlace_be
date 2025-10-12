package com.jnas.books_marketplace_be.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDTO implements Serializable {
    private Long userId;
    private ShippingAddress shippingAddress;

}
