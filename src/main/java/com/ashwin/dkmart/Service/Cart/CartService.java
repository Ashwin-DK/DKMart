package com.ashwin.dkmart.Service.Cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashwin.dkmart.Entity.Cart;
import com.ashwin.dkmart.Exception.ProductNotFoundException;
import com.ashwin.dkmart.Repository.CartItemRepository;
import com.ashwin.dkmart.Repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService implements ICartService{

    @Autowired 
    CartRepository cartRepository;

    @Autowired 
    CartItemRepository cartItemRepository;

     private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }


    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItem().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();

    }

    // @Override
    // public Cart getCartByUserId(Long userId) {
    //     return cartRepository.findByUserId(userId);
    // }
  
    
}
