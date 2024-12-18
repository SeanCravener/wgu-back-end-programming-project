package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
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

        if(cartItems.isEmpty()) {
            return "Purchase failed! You must have at least one item in your cart to purchase."
        } else if(cart.getParty_size < 1) {
            return "Purchase failed! Your party must be greater than zero."
        }
        cartItems.forEach(item -> {
            item.setCart(cart);
            cart.addCartItem(item);
        });
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(Status.ordered);
        cart.setCustomer(customer);
        customer.addCart(cart)
        cartRepository.save(cart);

        return new PurchaseResponse(orderTrackingNumber);
    }

}