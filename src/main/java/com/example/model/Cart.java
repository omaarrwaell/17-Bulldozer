package com.example.model;

import com.example.model.Product;

import java.util.*;

public class Cart {

    private UUID id;
    private UUID userId;
    private List<Product> products;

    // Constructor 1: No-args constructor (initializes an empty product list)
    public Cart() {
        this.id = UUID.randomUUID();
        this.products = new ArrayList<>();
    }

    // Constructor 2: Constructor with userId (generates a new cart with an empty product list)
    public Cart(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.products = new ArrayList<>();
    }

    // Constructor 3: Constructor with all fields
    public Cart(UUID id, UUID userId, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
