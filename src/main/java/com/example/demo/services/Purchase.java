package com.example.demo.services;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import java.util.Set;

@Data
@Getter
@Setter
public class Purchase {

    private Customer customer;

    private Cart cart;

    private Set<CartItem> cartItems;
}