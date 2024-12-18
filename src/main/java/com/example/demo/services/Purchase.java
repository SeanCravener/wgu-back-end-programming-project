package com.example.demo.services;

import com.example.entities.Cart;
import com.example.entities.CartItem;
import com.example.entities.Customer;
import lombok.*;
import java.util.Set;

@Getter
@Setter
public class Purchase {

    private Customer customer;

    private Cart cart;

    private Set<CartItem> cartItems;
}