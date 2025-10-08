package com.jnas.books_marketplace_be.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private List<CartItemDTO> items;
    private BigDecimal total;

}
