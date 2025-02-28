package com.example.service;

import com.example.model.Order;
import com.example.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService<Order> {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }

    // 7.5.2.3 Get a Specific Order
    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public void deleteOrderById(UUID orderId) {
        if (orderRepository.getOrderById(orderId) != null) {
            orderRepository.deleteOrderById(orderId);
        } else {
            throw new IllegalArgumentException("Order not found!");
        }
    }


}