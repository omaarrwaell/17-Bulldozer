package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class CartService extends MainService<Cart> {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository CartRepository) {
        this.cartRepository = CartRepository;
    }


    public Cart addCart(Cart cart) {
        return cartRepository.addCart(cart);
    }


    public ArrayList<Cart> getCarts() {
        return cartRepository.getCarts();
    }


    public Cart getCartById(UUID cartId) {
        return cartRepository.getCartById(cartId);
    }


    public Cart getCartByUserId(UUID userId) {
        return cartRepository.getCartByUserId(userId);
    }


    public void addProductToCart(UUID userId, UUID product) {
        cartRepository.addProductToCart(userId, product);
    }
    public void addProductToCart(UUID cartId, Product product) {
        cartRepository.addProductToCart(cartId, product);
    }


    public void deleteProductFromCart(UUID cartId, Product product) {
        cartRepository.deleteProductFromCart(cartId, product);
    }
    public void deleteProductFromCart(UUID userId, UUID productId) {
        cartRepository.deleteProductFromCart(userId, productId);
    }


    public void deleteCartById(UUID cartId) {
        cartRepository.deleteCartById(cartId);
    }
}
