package com.example.service;

import com.example.model.User;
import com.example.model.Order;
import com.example.model.Cart;
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

    @Autowired
    public UserService(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
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


        Order newOrder = new Order(UUID.randomUUID(), userId, cartService.calculateTotal(cart), cart.getProducts());


        userRepository.addOrderToUser(userId, newOrder);


        emptyCart(userId);
    }


    public void emptyCart(UUID userId) {
        cartService.clearCart(userId);
    }


    public void removeOrderFromUser(UUID userId, UUID orderId) {
        userRepository.removeOrderFromUser(userId, orderId);
    }

    public void deleteUserById(UUID userId) {
        userRepository.deleteUserById(userId);
    }
}
