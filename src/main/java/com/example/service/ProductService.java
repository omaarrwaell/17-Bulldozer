package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class ProductService extends MainService<Product> {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productRepository.getProductById(product.getId()) != null) {
            throw new IllegalArgumentException("Duplicate product with ID " + product.getId());
        }
        return productRepository.addProduct(product);
    }

    public ArrayList<Product> getProducts() {
        return productRepository.getProducts();
    }

    public Product getProductById(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Product product = productRepository.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }
        return product;
    }

    public Product updateProduct(UUID productId, String newName, double newPrice) {
        if (productId == null || newName == null || newName.isEmpty() || newPrice < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        return productRepository.updateProduct(productId, newName, newPrice);
    }

    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }

        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("Product IDs cannot be null or empty");
        }

        // Fetch all products
        ArrayList<Product> products = productRepository.getProducts();

        for (Product product : products) {
            if (productIds.contains(product.getId())) {
                double discountedPrice = product.getPrice() * (1 - discount / 100);
                product.setPrice(discountedPrice); // Update in memory
            }
        }

        // Save the updated products
        productRepository.saveAll(products);
    }


    public void deleteProductById(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Product product = productRepository.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }
        productRepository.deleteProductById(productId);
    }
}