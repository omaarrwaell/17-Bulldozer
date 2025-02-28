package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")

public class CartRepository extends MainRepository<Cart> {

    private static final String FILE_PATH = "com/example/data/carts.json";

    @Override
    protected String getDataPath() {
        return FILE_PATH;
    }
    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }
    public Cart addCart(Cart cart) {
        save(cart);
        return cart;
    }


    public ArrayList<Cart> getCarts() {
        return findAll();
    }


    public Cart getCartById(UUID cartId) {
        return findAll().stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .orElse(null);
    }


    public Cart getCartByUserId(UUID userId) {
        return findAll().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }


    public void addProductToCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = findAll();
        Optional<Cart> cartOptional = carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst();

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getProducts().add(product);
            overrideData(carts);
        }
    }

    public void deleteProductFromCart(UUID cartId, Product product) {
        ArrayList<Cart> carts = findAll();
        Optional<Cart> cartOptional = carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst();

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getProducts().removeIf(p -> p.getId().equals(product.getId()));
            overrideData(carts);
        }
    }

    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = findAll();
        carts.removeIf(cart -> cart.getId().equals(cartId));
        overrideData(carts);
    }

}

