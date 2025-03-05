package com.example.repository;

import com.example.model.Order;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User> {

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/users.json"; // Path to User data file
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class; // JSON mapping type
    }

    @Autowired
    private OrderRepository orderRepository;


    public ArrayList<User> getUsers() {
            return findAll();
        }

        public User getUserById(UUID userId) {
            return getUsers().stream()
                    .filter(user -> user.getId().equals(userId))
                    .findFirst()
                    .orElse(null);
        }

        public User addUser(User user) {
            save(user);
            return user;
        }


        public List<Order> getOrdersByUserId(UUID userId) {
            User user = getUserById(userId);
            return (user != null) ? user.getOrders() : new ArrayList<>();
        }


        public void addOrderToUser(UUID userId, Order order) {

            User user = getUserById(userId);
            user.getOrders().add(order);
            orderRepository.save(order);
            updateUser(user);

        }
    public void updateUser(User updatedUser) {
        ArrayList<User> users = getUsers(); // Fetch latest users

        // Replace existing user with the updated user
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                overrideData(users); // Save updated list to JSON file
                return;
            }
        }
    }

        public void removeOrderFromUser(UUID userId, UUID orderId) {
            ArrayList<User> users = getUsers();
            for (User user : users) {
                if (user.getId().equals(userId)) {
                    user.getOrders().removeIf(order -> order.getId().equals(orderId));
                    overrideData(users);

                }
            }
            orderRepository.deleteOrderById(orderId);
            orderRepository.overrideData(orderRepository.getOrders());
        }


        public void deleteUserById(UUID userId) {
            ArrayList<User> users = getUsers();
            users.removeIf(user -> user.getId().equals(userId));


            overrideData(users);
        }
    }


