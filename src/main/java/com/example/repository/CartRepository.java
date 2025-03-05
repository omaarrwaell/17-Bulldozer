package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void addProductToCart(UUID userId, UUID productId) {
        ArrayList<Cart> carts = findAll();
        Optional<Cart> cartOptional = carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst();

//        System.out.println(cartOptional);

        ArrayList<Product> products = productRepository.findAll();
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
//        System.out.println(products.get(0));
//        System.out.println(productOptional.get());
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getProducts().add(productOptional.get());
            overrideData(carts);
        }
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
    public void deleteProductFromCart(UUID userId, UUID productId) {
        ArrayList<Cart> carts = findAll();
        Optional<Cart> cartOptional = carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst();

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.getProducts().removeIf(p -> p.getId().equals(productId));
            overrideData(carts);
        }
    }

    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = findAll();
        carts.removeIf(cart -> cart.getId().equals(cartId));
        overrideData(carts);
    }
    public void updateCart(Cart updatedCart) {
        List<Cart> carts = getCarts(); // Fetch the latest carts from the JSON file

        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getUserId().equals(updatedCart.getUserId())) {
                carts.set(i, updatedCart); // Update the user's cart
                break;
            }
        }
        overrideData(new ArrayList<>(carts));
         // Save updated list back to the file
    }

}

