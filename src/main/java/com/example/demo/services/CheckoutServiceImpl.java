package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CartRepository cartRepository;

    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse checkout(Purchase purchase) {
        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();
        Set<CartItem> cartItems = purchase.getCartItems();
        String orderTrackingNumber = UUID.randomUUID().toString();

        if(cartItems.isEmpty()) {
            orderTrackingNumber = "Purchase failed! You must have at least one item in your cart to purchase.";
        } else if(cart.getParty_size < 1) {
            orderTrackingNumber = "Purchase failed! Your party must be greater than zero.";
        } else {
            cartItems.forEach(item -> {
                item.setCart(cart);
                cart.addCartItem(item);
            });
            cart.setOrderTrackingNumber(orderTrackingNumber);
            cart.setStatus(Status.ordered);
            cart.setCustomer(customer);
            customer.addCart(cart);
            cartRepository.save(cart);
        }
        return new PurchaseResponse(orderTrackingNumber);
    }

}