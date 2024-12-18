package com.example.demo.services;

import com.example.entities.Cart;
import com.example.entities.CartItem;
import com.example.entities.Customer;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;

@Getter
@Setter
public class Purchase {

    @Autowired
    private Customer customer;

    @Autowired
    private Cart cart;

    @Autowired
    private Set<CartItem> cartItems;
}