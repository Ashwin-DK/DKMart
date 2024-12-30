package com.ashwin.dkmart.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashwin.dkmart.Entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>{
    // Cart findByUserId(Long userId);
    
}
