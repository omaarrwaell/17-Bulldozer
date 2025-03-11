
package com.example.repository;
import com.example.repository.MainRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.example.model.Order;
import com.example.model.User;
import com.example.model.Product;
import com.example.model.Cart;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {
    private static final String DATA_PATH = "src/main/java/com/example/data/orders.json";
    public OrderRepository() {
    }
    @Value("${app.order.data.path}")
    private String orderDataPath;

    @Override
    protected String getDataPath() {
        return orderDataPath;
    }
    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }


    public void addOrder(Order order) {

        save(order);
    }

    public ArrayList<Order> getOrders() {
        return findAll();
    }

    public Order getOrderById(UUID orderId) {
        for (Order order : findAll()) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = findAll();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(orderId)) {
                orders.remove(i);
                break;
            }
        }
        overrideData(orders);
    }


}