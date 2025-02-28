package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class User {
    private UUID id;
    private String name;
    private List<Order> orders=new ArrayList<>();

    public User() {
        this.id = UUID.randomUUID();
        this.name = "";
        this.orders = new ArrayList<>();
    }

    // Constructor with only ID and name
    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.orders = new ArrayList<>();
    }

    // Constructor with all attributes
    public User(UUID id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders != null ? orders : new ArrayList<>();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
