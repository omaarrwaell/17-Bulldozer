package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Order {
    private UUID id;
    private UUID userId;
    private double totalPrice;
    private List<Product> products=new ArrayList<>();

    public UUID getId() {
        return id;
    }
}
