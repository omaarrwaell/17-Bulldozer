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

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (userRepository.getUserById(user.getId()) != null) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " already exists");
        }
        return userRepository.addUser(user);
    }


    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }


    public User getUserById(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }


    public List<Order> getOrdersByUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getOrders();
    }


    public void addOrderToUser(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
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
        emptyCart(userId);  // Ensure this method is called correctly
    }


    public void emptyCart(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user");
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
        if (userId == null || orderId == null) {
            throw new IllegalArgumentException("User ID and Order ID cannot be null");
        }
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Order orderToRemove = null;
        for (Order order : user.getOrders()) {
            if (order.getId().equals(orderId)) {
                orderToRemove = order;
                break;
            }
        }
        if (orderToRemove == null) {
            throw new IllegalArgumentException("Order not found");
        }
        user.getOrders().remove(orderToRemove);
        userRepository.updateUser(user);
    }

    public void deleteUserById(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteUserById(userId);
    }
}
