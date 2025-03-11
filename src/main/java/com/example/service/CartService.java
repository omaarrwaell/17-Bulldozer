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
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        return cartRepository.addCart(cart);
    }

    public ArrayList<Cart> getCarts() {
        return cartRepository.getCarts();
    }

    public Cart getCartById(UUID cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        return cartRepository.getCartById(cartId);
    }

    public Cart getCartByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return cartRepository.getCartByUserId(userId);
    }

    public void addProductToCartByUserId(UUID userId, UUID productId) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("User ID and Product ID cannot be null");
        }
        cartRepository.addProductToCartByUserId(userId, productId);
    }

    public void addProductToCart(UUID cartId, Product product) {
        if (cartId == null || product == null) {
            throw new IllegalArgumentException("Cart ID and Product cannot be null");
        }
        cartRepository.addProductToCart(cartId, product);
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        if (cartId == null || product == null) {
            throw new IllegalArgumentException("Cart ID and Product cannot be null");
        }
        cartRepository.deleteProductFromCart(cartId, product);
    }

    public String deleteProductFromCartByUserId(UUID userId, UUID productId) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("User ID and Product ID cannot be null");
        }
      return  cartRepository.deleteProductFromCartByUserId(userId, productId);
    }

    public void deleteCartById(UUID cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }

        Cart cart = cartRepository.getCartById(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart with ID " + cartId + " not found");
        }

        cartRepository.deleteCartById(cartId);
    }
}