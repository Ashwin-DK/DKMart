package com.ashwin.dkmart.Service.Cart;

import java.math.BigDecimal;

import com.ashwin.dkmart.Entity.Cart;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    // Cart getCartByUserId(Long userId);
    
}
