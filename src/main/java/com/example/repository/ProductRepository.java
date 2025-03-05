package com.example.repository;

import com.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/products.json";
    }

    public Product addProduct(Product product) {
        try {
            logger.info("Adding new product: {}", product.getName());
            ArrayList<Product> products = findAll();
            products.add(product);
            saveAll(products);
            return product;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to add product: " + e.getMessage(), e);
        }
    }

    public ArrayList<Product> getProducts() {
        try {
            return findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve products: " + e.getMessage(), e);
        }
    }

    public Product getProductById(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        try {
            return findAll().stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst()
                    .orElse(null);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve product: " + e.getMessage(), e);
        }
    }

    public Product updateProduct(UUID productId, String newName, double newPrice) {
        if (productId == null || newName == null || newName.isEmpty() || newPrice < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        try {
            ArrayList<Product> products = findAll();
            for (Product product : products) {
                if (product.getId().equals(productId)) {
                    product.setName(newName);
                    product.setPrice(newPrice);
                    saveAll(products);
                    return product;
                }
            }
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

    public void applyDiscount(double discount, ArrayList<UUID> productIds) {
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }

        try {
            ArrayList<Product> products = findAll();
            for (Product product : products) {
                if (productIds.contains(product.getId())) {
                    double discountedPrice = product.getPrice() * (1 - discount / 100);
                    product.setPrice(discountedPrice);
                }
            }
            saveAll(products);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to apply discount: " + e.getMessage(), e);
        }
    }

    public void deleteProductById(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        try {
            ArrayList<Product> products = findAll();
            products.removeIf(product -> product.getId().equals(productId));
            saveAll(products);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }
}