package com.example.MiniProject1;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OurTests {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CartService cartService;
    @InjectMocks
    private OrderService orderService;
    @InjectMocks
    private ProductService productService;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCartServiceAddCart_Success() {
        Cart cart = new Cart();
        when(cartRepository.addCart(cart)).thenReturn(cart);

        Cart addedCart = cartService.addCart(cart);

        assertNotNull(addedCart);
        assertEquals(cart, addedCart);
        verify(cartRepository, times(1)).addCart(cart);
    }

    @Test
    void testCartServiceAddCart_NullCart() {
        when(cartRepository.addCart(null)).thenThrow(new IllegalArgumentException("Cart cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> cartService.addCart(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(1)).addCart(null);
    }

    @Test
    void testCartServiceAddCart_CartWithExistingId() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.addCart(cart)).thenReturn(cart);

        Cart addedCart = cartService.addCart(cart);

        assertNotNull(addedCart);
        assertEquals(cartId, addedCart.getId());
        verify(cartRepository, times(1)).addCart(cart);
    }


    @Test
    void testCartServiceGetCarts() {
        List<Cart> carts = new ArrayList<>();
        carts.add(new Cart());
        carts.add(new Cart());
        when(cartRepository.getCarts()).thenReturn((ArrayList<Cart>) carts);

        ArrayList<Cart> retrievedCarts = cartService.getCarts();

        assertNotNull(retrievedCarts);
        assertEquals(2, retrievedCarts.size());
        verify(cartRepository, times(1)).getCarts();
    }
    @Test
    void testCartServiceGetCarts_EmptyList() {
        when(cartRepository.getCarts()).thenReturn(new ArrayList<>());

        ArrayList<Cart> retrievedCarts = cartService.getCarts();

        assertNotNull(retrievedCarts);
        assertEquals(0, retrievedCarts.size());
        verify(cartRepository, times(1)).getCarts();
    }

    @Test
    void testCartServiceGetCarts_RepositoryReturnsNull() {
        when(cartRepository.getCarts()).thenReturn(null);

        ArrayList<Cart> retrievedCarts = cartService.getCarts();

        assertNull(retrievedCarts);
        verify(cartRepository, times(1)).getCarts();
    }


    @Test
    void testCartServiceGetCartById() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setId(cartId);
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        Cart retrievedCart = cartService.getCartById(cartId);

        assertNotNull(retrievedCart);
        assertEquals(cartId, retrievedCart.getId());
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testCartServiceGetCartById_NotFound() {
        UUID cartId = UUID.randomUUID();
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        Cart retrievedCart = cartService.getCartById(cartId);

        assertNull(retrievedCart);
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testCartServiceGetCartById_InvalidId() {
        when(cartRepository.getCartById(null)).thenThrow(new IllegalArgumentException("Cart ID cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> cartService.getCartById(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(1)).getCartById(null);
    }

    @Test
    void testCartServiceGetCartByUserId() {
        UUID userId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setUserId(userId);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        Cart retrievedCart = cartService.getCartByUserId(userId);

        assertNotNull(retrievedCart);
        assertEquals(userId, retrievedCart.getUserId());
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }

    @Test
    void testCartServiceGetCartByUserId_NotFound() {
        UUID userId = UUID.randomUUID();
        when(cartRepository.getCartByUserId(userId)).thenReturn(null);

        Cart retrievedCart = cartService.getCartByUserId(userId);

        assertNull(retrievedCart);
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }
    @Test
    void testCartServiceGetCartByUserId_InvalidId() {
        when(cartRepository.getCartByUserId(null)).thenThrow(new IllegalArgumentException("User ID cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> cartService.getCartByUserId(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(1)).getCartByUserId(null);
    }

    @Test
    void testCartServiceAddProductToCart() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product();

        cartService.addProductToCart(cartId, product);

        verify(cartRepository, times(1)).addProductToCart(cartId, product);
    }
    @Test
    void testCartServiceAddProductToCart_NullCartId() {
        Product product = new Product();

        assertThrows(NullPointerException.class, () -> cartService.addProductToCart(null, product), "Expected NullPointerException to be thrown");
        verify(cartRepository, times(0)).addProductToCart(null, product);
    }

    @Test
    void testCartServiceAddProductToCart_NullProduct() {
        UUID cartId = UUID.randomUUID();
        assertThrows(NullPointerException.class, () -> cartService.addProductToCart(cartId, null), "Expected NullPointerException to be thrown");
        verify(cartRepository, times(0)).addProductToCart(cartId, null);
    }

    @Test
    void testCartServiceDeleteProductFromCart() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product();

        cartService.deleteProductFromCart(cartId, product);

        verify(cartRepository, times(1)).deleteProductFromCart(cartId, product);
    }
    @Test
    void testCartServiceDeleteProductFromCart_NullCartId() {
        Product product = new Product();

        assertThrows(NullPointerException.class, () -> cartService.deleteProductFromCart(null, product), "Expected NullPointerException to be thrown");
        verify(cartRepository, times(0)).deleteProductFromCart(null, product);
    }

    @Test
    void testCartServiceDeleteProductFromCart_NullProduct() {
        UUID cartId = UUID.randomUUID();
        assertThrows(NullPointerException.class, () -> cartService.deleteProductFromCart(cartId, null), "Expected NullPointerException to be thrown");
        verify(cartRepository, times(0)).deleteProductFromCart(cartId, null);
    }
    @Test
    void testCartServiceDeleteCartById() {
        UUID cartId = UUID.randomUUID();

        cartService.deleteCartById(cartId);

        verify(cartRepository, times(1)).deleteCartById(cartId);
    }

    @Test
    void testCartServiceDeleteCartById_NullId() {

        assertThrows(NullPointerException.class, () -> cartService.deleteCartById(null), "Expected NullPointerException to be thrown");
        verify(cartRepository, times(0)).deleteCartById(null);
    }

    @Test
    void testCartServiceDeleteCartById_CartNotFound() {
        UUID cartId = UUID.randomUUID();

        doNothing().when(cartRepository).deleteCartById(cartId);

        cartService.deleteCartById(cartId);

        verify(cartRepository, times(1)).deleteCartById(cartId);
    }

    // ------------------------ OrderService Tests -------------------------

//    @Test
//    void testOrderServiceAddOrder() {
//        Order order = new Order();
//        when(orderRepository.addOrder(order)).thenReturn(order);
//
//        Order addedOrder = orderService.addOrder(order);
//
//        assertNotNull(addedOrder);
//        assertEquals(order, addedOrder);
//        verify(orderRepository, times(1)).addOrder(order);
//    }
    @Test
    void testOrderServiceAddOrder_NullOrder() {

        assertThrows(NullPointerException.class, () -> orderService.addOrder(null), "Expected NullPointerException to be thrown");
        verify(orderRepository, times(0)).addOrder(null);
    }
//    @Test
//    void testOrderServiceAddOrder_OrderWithExistingId() {
//        UUID orderId = UUID.randomUUID();
//        Order order = new Order();
//        order.setId(orderId);
//        when(orderRepository.addOrder(order)).thenReturn(order);
//
//        Order addedOrder = orderService.addOrder(order);
//
//        assertNotNull(addedOrder);
//        assertEquals(orderId, addedOrder.getId());
//        verify(orderRepository, times(1)).addOrder(order);
//    }

    @Test
    void testOrderServiceGetOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderRepository.getOrders()).thenReturn((ArrayList<Order>) orders);

        ArrayList<Order> retrievedOrders = orderService.getOrders();

        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
        verify(orderRepository, times(1)).getOrders();
    }
    @Test
    void testOrderServiceGetOrders_EmptyList() {
        when(orderRepository.getOrders()).thenReturn(new ArrayList<>());

        ArrayList<Order> retrievedOrders = orderService.getOrders();

        assertNotNull(retrievedOrders);
        assertEquals(0, retrievedOrders.size());
        verify(orderRepository, times(1)).getOrders();
    }
    @Test
    void testOrderServiceGetOrders_RepositoryReturnsNull() {
        when(orderRepository.getOrders()).thenReturn(null);

        ArrayList<Order> retrievedOrders = orderService.getOrders();

        assertNull(retrievedOrders);
        verify(orderRepository, times(1)).getOrders();
    }

    @Test
    void testOrderServiceGetOrderById() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.getOrderById(orderId)).thenReturn(order);

        Order retrievedOrder = orderService.getOrderById(orderId);

        assertNotNull(retrievedOrder);
        assertEquals(orderId, retrievedOrder.getId());
        verify(orderRepository, times(1)).getOrderById(orderId);
    }

    @Test
    void testOrderServiceGetOrderById_NotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.getOrderById(orderId)).thenReturn(null);

        Order retrievedOrder = orderService.getOrderById(orderId);

        assertNull(retrievedOrder);
        verify(orderRepository, times(1)).getOrderById(orderId);
    }
    @Test
    void testOrderServiceGetOrderById_NullId() {

        assertThrows(NullPointerException.class, () -> orderService.getOrderById(null), "Expected NullPointerException to be thrown");
        verify(orderRepository, times(0)).getOrderById(null);
    }

    @Test
    void testOrderServiceDeleteOrderById() {
        UUID orderId = UUID.randomUUID();

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteOrderById(orderId);
    }
    @Test
    void testOrderServiceDeleteOrderById_NullId() {
        assertThrows(NullPointerException.class, () -> orderService.deleteOrderById(null), "Expected NullPointerException to be thrown");
        verify(orderRepository, times(0)).deleteOrderById(null);
    }

    @Test
    void testOrderServiceDeleteOrderById_OrderNotFound() {
        UUID orderId = UUID.randomUUID();

        doNothing().when(orderRepository).deleteOrderById(orderId);

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteOrderById(orderId);
    }

    // ------------------------ ProductService Tests -------------------------

    @Test
    void testProductServiceAddProduct() {
        Product product = new Product();
        when(productRepository.addProduct(product)).thenReturn(product);

        Product addedProduct = productService.addProduct(product);

        assertNotNull(addedProduct);
        assertEquals(product, addedProduct);
        verify(productRepository, times(1)).addProduct(product);
    }
    @Test
    void testProductServiceAddProduct_NullProduct() {
        assertThrows(NullPointerException.class, () -> productService.addProduct(null), "Expected NullPointerException to be thrown");
        verify(productRepository, times(0)).addProduct(null);
    }

    @Test
    void testProductServiceAddProduct_ProductWithExistingId() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        when(productRepository.addProduct(product)).thenReturn(product);

        Product addedProduct = productService.addProduct(product);

        assertNotNull(addedProduct);
        assertEquals(productId, addedProduct.getId());
        verify(productRepository, times(1)).addProduct(product);
    }

    @Test
    void testProductServiceGetProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.getProducts()).thenReturn((ArrayList<Product>) products);

        ArrayList<Product> retrievedProducts = productService.getProducts();

        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
        verify(productRepository, times(1)).getProducts();
    }
    @Test
    void testProductServiceGetProducts_EmptyList() {
        when(productRepository.getProducts()).thenReturn(new ArrayList<>());

        ArrayList<Product> retrievedProducts = productService.getProducts();

        assertNotNull(retrievedProducts);
        assertEquals(0, retrievedProducts.size());
        verify(productRepository, times(1)).getProducts();
    }
    @Test
    void testProductServiceGetProducts_RepositoryReturnsNull() {
        when(productRepository.getProducts()).thenReturn(null);

        ArrayList<Product> retrievedProducts = productService.getProducts();

        assertNull(retrievedProducts);
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testProductServiceGetProductById() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        when(productRepository.getProductById(productId)).thenReturn(product);

        Product retrievedProduct = productService.getProductById(productId);

        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getId());
        verify(productRepository, times(1)).getProductById(productId);
    }
    @Test
    void testProductServiceGetProductById_NotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.getProductById(productId)).thenReturn(null);

        Product retrievedProduct = productService.getProductById(productId);

        assertNull(retrievedProduct);
        verify(productRepository, times(1)).getProductById(productId);
    }
    @Test
    void testProductServiceGetProductById_NullId() {
        assertThrows(NullPointerException.class, () -> productService.getProductById(null), "Expected NullPointerException to be thrown");
        verify(productRepository, times(0)).getProductById(null);
    }

    @Test
    void testProductServiceUpdateProduct() {
        UUID productId = UUID.randomUUID();
        String newName = "Updated Product";
        double newPrice = 20.0;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName(newName);
        updatedProduct.setPrice(newPrice);

        when(productRepository.updateProduct(productId, newName, newPrice)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, newName, newPrice);

        assertNotNull(result);
        assertEquals(newName, result.getName());
        assertEquals(newPrice, result.getPrice());
        verify(productRepository, times(1)).updateProduct(productId, newName, newPrice);
    }
    @Test
    void testProductServiceUpdateProduct_NullId() {
        String newName = "Updated Product";
        double newPrice = 20.0;

        assertThrows(NullPointerException.class, () -> productService.updateProduct(null, newName, newPrice), "Expected NullPointerException to be thrown");
        verify(productRepository, times(0)).updateProduct(null, newName, newPrice);
    }

    @Test
    void testProductServiceUpdateProduct_InvalidPrice() {
        UUID productId = UUID.randomUUID();
        String newName = "Updated Product";
        double newPrice = -20.0;

        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productId, newName, newPrice), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).updateProduct(productId, newName, newPrice);
    }
//    @Test
//    void testProductServiceApplyDiscount() {
//        List<UUID> productIds = new ArrayList<>();
//        UUID productId = UUID.randomUUID();
//        productIds.add(productId);
//        double discount = 10.0;
//
//        productService.applyDiscount(productIds, discount);
//
//        verify(productRepository, times(1)).applyDiscount(productIds, discount);
//    }
//
//    @Test
//    void testProductServiceApplyDiscount_NullProductIds() {
//        double discount = 10.0;
//
//        assertThrows(NullPointerException.class, () -> productService.applyDiscount(null, discount), "Expected NullPointerException to be thrown");
//        verify(productRepository, times(0)).applyDiscount(null, discount);
//    }
//
//    @Test
//    void testProductServiceApplyDiscount_InvalidDiscount() {
//        List<UUID> productIds = new ArrayList<>();
//        UUID productId = UUID.randomUUID();
//        productIds.add(productId);
//        double discount = -10.0;
//
//        assertThrows(IllegalArgumentException.class, () -> productService.applyDiscount(productIds, discount), "Expected IllegalArgumentException to be thrown");
//        verify(productRepository, times(0)).applyDiscount(productIds, discount);
//    }
    @Test
    void testProductServiceDeleteProductById() {
        UUID productId = UUID.randomUUID();

        productService.deleteProductById(productId);

        verify(productRepository, times(1)).deleteProductById(productId);
    }
    @Test
    void testProductServiceDeleteProductById_NullId() {

        assertThrows(NullPointerException.class, () -> productService.deleteProductById(null), "Expected NullPointerException to be thrown");
        verify(productRepository, times(0)).deleteProductById(null);
    }

    @Test
    void testProductServiceDeleteProductById_ProductNotFound() {
        UUID productId = UUID.randomUUID();

        doNothing().when(productRepository).deleteProductById(productId);

        productService.deleteProductById(productId);

        verify(productRepository, times(1)).deleteProductById(productId);
    }

    // ------------------------ UserService Tests -------------------------

    @Test
    void testUserServiceAddUser() {
        User user = new User();
        when(userRepository.addUser(user)).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertNotNull(addedUser);
        assertEquals(user, addedUser);
        verify(userRepository, times(1)).addUser(user);
    }
    @Test
    void testUserServiceAddUser_NullUser() {
        assertThrows(NullPointerException.class, () -> userService.addUser(null), "Expected NullPointerException to be thrown");
        verify(userRepository, times(0)).addUser(null);
    }

    @Test
    void testUserServiceAddUser_UserWithExistingId() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        when(userRepository.addUser(user)).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertNotNull(addedUser);
        assertEquals(userId, addedUser.getId());
        verify(userRepository, times(1)).addUser(user);
    }

    @Test
    void testUserServiceGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.getUsers()).thenReturn((ArrayList<User>) users);

        ArrayList<User> retrievedUsers = userService.getUsers();

        assertNotNull(retrievedUsers);
        assertEquals(2, retrievedUsers.size());
        verify(userRepository, times(1)).getUsers();
    }
    @Test
    void testUserServiceGetUsers_EmptyList() {
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        ArrayList<User> retrievedUsers = userService.getUsers();

        assertNotNull(retrievedUsers);
        assertEquals(0, retrievedUsers.size());
        verify(userRepository, times(1)).getUsers();
    }
    @Test
    void testUserServiceGetUsers_RepositoryReturnsNull() {
        when(userRepository.getUsers()).thenReturn(null);

        ArrayList<User> retrievedUsers = userService.getUsers();

        assertNull(retrievedUsers);
        verify(userRepository, times(1)).getUsers();
    }

    @Test
    void testUserServiceGetUserById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        when(userRepository.getUserById(userId)).thenReturn(user);

        User retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
        verify(userRepository, times(1)).getUserById(userId);
    }
    @Test
    void testUserServiceGetUserById_NotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.getUserById(userId)).thenReturn(null);

        User retrievedUser = userService.getUserById(userId);

        assertNull(retrievedUser);
        verify(userRepository, times(1)).getUserById(userId);
    }

    @Test
    void testUserServiceGetUserById_NullId() {
        assertThrows(NullPointerException.class, () -> userService.getUserById(null), "Expected NullPointerException to be thrown");
        verify(userRepository, times(0)).getUserById(null);
    }

    @Test
    void testUserServiceDeleteUserById() {
        UUID userId = UUID.randomUUID();

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteUserById(userId);
    }
    @Test
    void testUserServiceDeleteUserById_NullId() {
        assertThrows(NullPointerException.class, () -> userService.deleteUserById(null), "Expected NullPointerException to be thrown");
        verify(userRepository, times(0)).deleteUserById(null);
    }

    @Test
    void testUserServiceDeleteUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();

        doNothing().when(userRepository).deleteUserById(userId);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteUserById(userId);
    }
}