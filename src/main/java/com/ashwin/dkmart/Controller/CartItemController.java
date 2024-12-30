package com.ashwin.dkmart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashwin.dkmart.Exception.ProductNotFoundException;
import com.ashwin.dkmart.Response.ApiResponse;
import com.ashwin.dkmart.Service.Cart.ICartItemService;
import com.ashwin.dkmart.Service.Cart.ICartService;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("${api.prefix}/cartitems")
public class CartItemController {
    @Autowired
    ICartItemService cartItemService;
    @Autowired
    ICartService cartService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartid,
                                                     @RequestParam Long productid,
                                                     @RequestParam Integer quantity) {
        try {
            if (cartid == null) {
                cartid = cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartid, productid, quantity);
            return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemid}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartid}/item/{itemid}/update")
    public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartid,
                                                           @PathVariable Long itemid,
                                                           @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartid, itemid, quantity);
            return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
