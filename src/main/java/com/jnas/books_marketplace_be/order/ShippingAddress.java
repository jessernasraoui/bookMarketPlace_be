package com.jnas.books_marketplace_be.order;


import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingAddress {
    private String street;
    private String city;
    private String postalCode;
}
