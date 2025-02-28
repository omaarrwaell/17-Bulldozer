package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.example.model.Product;


@Component
public class Cart {
    private UUID id;
    private UUID userId;

    private List<Product> products=new ArrayList<>();
}
