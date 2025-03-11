package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")

public class CartRepository extends MainRepository<Cart> {

    private static final String FILE_PATH = "src/main/java/com/example/data/carts.json";
    @Autowired
    private final ProductRepository productRepository;

    public CartRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Value("${app.cart.data.path}")
    private String cartDataPath;
    @Override
    protected String getDataPath() {
        return cartDataPath;
    }
    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }
    public Cart addCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }

        if (getCartById(cart.getId()) != null) {
            throw new IllegalArgumentException("Cart with the same ID already exists");
        }

        save(cart);
        return cart;
    }


    public ArrayList<Cart> getCarts() {
        return findAll();
    }

    public Cart getCartById(UUID cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        return findAll().stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .orElse(null);
    }

    public Cart getCartByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return findAll().stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void addProductToCartByUserId(UUID userId, UUID productId) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("User ID and Product ID cannot be null");
        }
        ArrayList<Cart> carts = findAll();
        Optional<Cart> cartOptional = carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst();

        ArrayList<Product> products = productRepository.findAll();
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();

        if (cartOptional.isPresent() && productOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getProducts().add(productOptional.get());
            overrideData(carts);
        }
    }

    public void addProductToCart(UUID cartId, Product product) {
        if (cartId == null || product == null) {
            throw new IllegalArgumentException("Cart ID and Product cannot be null");
        }
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
        if (cartId == null || product == null) {
            throw new IllegalArgumentException("Cart ID and Product cannot be null");
        }
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

    public String deleteProductFromCartByUserId(UUID userId, UUID productId) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("User ID and Product ID cannot be null");
        }
        ArrayList<Cart> carts = findAll();
        Optional<Cart> cartOptional = carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst();

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            if(cart.getProducts().isEmpty()) {
                return "Cart is empty";
            }
            cart.getProducts().removeIf(p -> p.getId().equals(productId));

            overrideData(carts);

        } else if (!cartOptional.isPresent() ) {
            return "Cart is empty";

        }
        return "Product deleted from cart";
    }

    public void deleteCartById(UUID cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID cannot be null");
        }
        ArrayList<Cart> carts = findAll();
        boolean removed = carts.removeIf(cart -> cart.getId().equals(cartId));
        if (!removed) {
            throw new IllegalArgumentException("Cart with ID " + cartId + " not found");
        }
        overrideData(carts);
    }

    public void updateCart(Cart updatedCart) {
        if (updatedCart == null) {
            throw new IllegalArgumentException("Updated cart cannot be null");
        }
        List<Cart> carts = getCarts();

        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getUserId().equals(updatedCart.getUserId())) {
                carts.set(i, updatedCart);
                break;
            }
        }
        overrideData(new ArrayList<>(carts));
    }
}