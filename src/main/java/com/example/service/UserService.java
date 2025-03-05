package com.example.service;

import com.example.model.Product;
import com.example.model.User;
import com.example.model.Order;
import com.example.model.Cart;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends MainService<User> {

    private final UserRepository userRepository;
    private final CartService cartService;  // Injecting CartService
    private final CartRepository cartRepository;
    @Autowired
    public UserService(UserRepository userRepository, CartService cartService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }


    public User addUser(User user) {
        return userRepository.addUser(user);
    }


    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }


    public User getUserById(UUID userId) {
        return userRepository.getUserById(userId);
    }


    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }


    public void addOrderToUser(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null || cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Cart is empty or not found.");
        }
        double totalPrice = 0;
        for (Product product : cart.getProducts()) {
            totalPrice += product.getPrice();
        }

        Order newOrder = new Order(UUID.randomUUID(), userId, totalPrice, cart.getProducts());


        userRepository.addOrderToUser(userId, newOrder);


        emptyCart(userId);
    }


    public void emptyCart(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);

        if (cart == null) {
            System.out.println("Cart not found for user: " + userId);
            return;
        }
        System.out.println("Before emptying: " + cart.getProducts());

        if (cart.getProducts() == null) {
            cart.setProducts(new ArrayList<>());
            System.out.println("Cart");// Initialize if null
        } else {
            cart.getProducts().clear();  // Clear instead of replacing
        }

        System.out.println("Cart products after emptying: " + cart.getProducts());
        System.out.println("Cart products after ying: " + cartRepository.getCarts().get(0).getProducts().get(0).getName());

        // Ensure the updated cart is saved back
        cartRepository.updateCart(cart);
//        System.out.println("Cart products after ng: " + cartRepository.getCarts().get(0).getProducts().get(0).getName());
//        cartRepository.overrideData(cartRepository.getCarts()); // Ensure this writes the updated carts
    }



    public void removeOrderFromUser(UUID userId, UUID orderId) {
        userRepository.removeOrderFromUser(userId, orderId);
    }

    public void deleteUserById(UUID userId) {
        userRepository.deleteUserById(userId);
    }
}
