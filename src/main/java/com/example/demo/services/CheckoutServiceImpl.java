package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Customer;
import com.example.demo.entity.StatusType;
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
        Cart cart = new Cart();
        Cart purchaseCart = purchase.getCart();
        String orderTrackingNumber;

        // Validate party size
        if (cart.getParty_size() <= 0) {
            orderTrackingNumber = "Invalid: Party size must be greater than 0";
            return new PurchaseResponse(orderTrackingNumber);
        }
    
        // Validate cart is not empty
        if (purchase.getCartItems() == null || purchase.getCartItems().isEmpty()) {
            orderTrackingNumber = "Invalid: Cart cannot be empty";
            return new PurchaseResponse(orderTrackingNumber);
        }
        
        cart.setPackage_price(purchaseCart.getPackage_price());
        cart.setParty_size(purchaseCart.getParty_size());
        
        orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ordered);
        cart.setCustomer(purchase.getCustomer());
        
        purchase.getCartItems().forEach(item -> {
            cart.addCartItem(item);
        });
        
        cartRepository.save(cart);

        return new PurchaseResponse(orderTrackingNumber);
    }

}