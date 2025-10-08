package com.jnas.books_marketplace_be.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bookId;
    private String bookTitle;
    private int quantity;
    private BigDecimal totalPrice;

}
