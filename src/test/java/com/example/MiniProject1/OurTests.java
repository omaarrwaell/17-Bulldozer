package com.example.service;


import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
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

    // ------------------------ CartService Tests -------------------------

    @Test
    void testAddCart_Success() {
        Cart cart = new Cart();
        when(cartRepository.addCart(cart)).thenReturn(cart);

        Cart addedCart = cartService.addCart(cart);

        assertNotNull(addedCart);
        assertEquals(cart, addedCart);
        verify(cartRepository, times(1)).addCart(cart);
    }


    @Test
    void testCartServiceAddCart_NullCart() {
        assertThrows(IllegalArgumentException.class, () -> cartService.addCart(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).addCart(null);
    }

    @Test
    void testAddCart_CartWithExistingId() {
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
    void testGetCarts_Success() {
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
    void testGetCarts_EmptyList() {
        when(cartRepository.getCarts()).thenReturn(new ArrayList<>());

        ArrayList<Cart> retrievedCarts = cartService.getCarts();

        assertNotNull(retrievedCarts);
        assertEquals(0, retrievedCarts.size());
        verify(cartRepository, times(1)).getCarts();
    }

    @Test
    void testGetCarts_RepositoryReturnsNull() {
        when(cartRepository.getCarts()).thenReturn(null);

        ArrayList<Cart> retrievedCarts = cartService.getCarts();

        assertNull(retrievedCarts);
        verify(cartRepository, times(1)).getCarts();
    }

    @Test
    void testGetCartById_Success() {
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
    void testGetCartById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> cartService.getCartById(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).getCartById(null);
    }

    @Test
    void testGetCartById_NotFound() {
        UUID cartId = UUID.randomUUID();
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        Cart retrievedCart = cartService.getCartById(cartId);

        assertNull(retrievedCart);
        verify(cartRepository, times(1)).getCartById(cartId);
    }

    @Test
    void testGetCartByUserId_Success() {
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
    void testGetCartByUserId_NullId() {
        assertThrows(IllegalArgumentException.class, () -> cartService.getCartByUserId(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).getCartByUserId(null);
    }

    @Test
    void testGetCartByUserId_NotFound() {
        UUID userId = UUID.randomUUID();
        when(cartRepository.getCartByUserId(userId)).thenReturn(null);

        Cart retrievedCart = cartService.getCartByUserId(userId);

        assertNull(retrievedCart);
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }


    @Test
    void testAddProductToCartByUserId_Success() {
        UUID userId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        Cart cart = new Cart();
        cart.setUserId(userId);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(product)));

        cartService.addProductToCartByUserId(userId, productId);

        verify(cartRepository, times(1)).addProductToCartByUserId(userId, productId);
    }

    @Test
    void testAddProductToCartByUserId_NullUserId() {
        UUID productId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCartByUserId(null, productId), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).addProductToCartByUserId(null, productId);
    }

    @Test
    void testAddProductToCartByUserId_NullProductId() {
        UUID userId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCartByUserId(userId, null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).addProductToCartByUserId(userId, null);
    }

    @Test
    void testAddProductToCart_Success() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product();
        Cart cart = new Cart();
        cart.setId(cartId);
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.addProductToCart(cartId, product);

        verify(cartRepository, times(1)).addProductToCart(cartId, product);
    }

    @Test
    void testAddProductToCart_NullCartId() {
        Product product = new Product();

        assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCart(null, product), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).addProductToCart(null, product);
    }

    @Test
    void testAddProductToCart_NullProduct() {
        UUID cartId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> cartService.addProductToCart(cartId, null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).addProductToCart(cartId, null);
    }

    @Test
    void testDeleteProductFromCart_Success() {
        UUID cartId = UUID.randomUUID();
        Product product = new Product();
        Cart cart = new Cart();
        cart.setId(cartId);
        when(cartRepository.getCartById(cartId)).thenReturn(cart);

        cartService.deleteProductFromCart(cartId, product);

        verify(cartRepository, times(1)).deleteProductFromCart(cartId, product);
    }

    @Test
    void testDeleteProductFromCart_NullCartId() {
        Product product = new Product();

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteProductFromCart(null, product), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).deleteProductFromCart(null, product);
    }

    @Test
    void testDeleteProductFromCart_NullProduct() {
        UUID cartId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteProductFromCart(cartId, null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).deleteProductFromCart(cartId, null);
    }


    @Test
    void testDeleteProductFromCartByUserId_Success() {
        UUID userId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        Cart cart = new Cart();
        cart.setUserId(userId);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(product)));

        cartService.deleteProductFromCartByUserId(userId, productId);

        verify(cartRepository, times(1)).deleteProductFromCartByUserId(userId, productId);
    }

    @Test
    void testDeleteProductFromCartByUserId_NullUserId() {
        UUID productId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteProductFromCartByUserId(null, productId), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).deleteProductFromCartByUserId(null, productId);
    }

    @Test
    void testDeleteProductFromCartByUserId_NullProductId() {
        UUID userId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteProductFromCartByUserId(userId, null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).deleteProductFromCartByUserId(userId, null);
    }

    @Test
    void testDeleteCartById_Success() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.getCartById(cartId)).thenReturn(cart).thenReturn(null);
        doNothing().when(cartRepository).deleteCartById(cartId);

        cartService.deleteCartById(cartId);

        verify(cartRepository, times(1)).deleteCartById(cartId);
        assertNull(cartService.getCartById(cartId));
    }

    @Test
    void testDeleteCartById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> cartService.deleteCartById(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).deleteCartById(null);
    }

    @Test
    void testDeleteCartById_NotFound() {
        UUID cartId = UUID.randomUUID();
        when(cartRepository.getCartById(cartId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> cartService.deleteCartById(cartId), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).deleteCartById(cartId);
    }

    // ------------------------ OrderService Tests -------------------------

    @Test
    void testAddOrder_Success() {
        Order order = new Order();
        when(orderRepository.getOrderById(order.getId())).thenReturn(null);
        doNothing().when(orderRepository).addOrder(order);

        orderService.addOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    void testAddOrder_NullOrder() {
        assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(null), "Expected IllegalArgumentException to be thrown");
        verify(orderRepository, times(0)).addOrder(null);
    }

    @Test
    void testAddOrder_DuplicateOrder() {
        Order order = new Order();
        when(orderRepository.getOrderById(order.getId())).thenReturn(order);

        assertThrows(IllegalArgumentException.class, () -> orderService.addOrder(order), "Expected IllegalArgumentException to be thrown");
        verify(orderRepository, times(0)).addOrder(order);
    }

    @Test
    void testGetOrders_Success() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderRepository.getOrders()).thenReturn(orders);

        ArrayList<Order> retrievedOrders = orderService.getOrders();

        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
        verify(orderRepository, times(1)).getOrders();
    }

    @Test
    void testGetOrders_EmptyList() {
        when(orderRepository.getOrders()).thenReturn(new ArrayList<>());

        ArrayList<Order> retrievedOrders = orderService.getOrders();

        assertNotNull(retrievedOrders);
        assertEquals(0, retrievedOrders.size());
        verify(orderRepository, times(1)).getOrders();
    }

    @Test
    void testGetOrders_RepositoryReturnsNull() {
        when(orderRepository.getOrders()).thenReturn(null);

        ArrayList<Order> retrievedOrders = orderService.getOrders();

        assertNull(retrievedOrders);
        verify(orderRepository, times(1)).getOrders();
    }

    @Test
    void testGetOrderById_Success() {
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
    void testGetOrderById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(null), "Expected IllegalArgumentException to be thrown");
        verify(orderRepository, times(0)).getOrderById(null);
    }

    @Test
    void testGetOrderById_NotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.getOrderById(orderId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> orderService.getOrderById(orderId), "Expected IllegalArgumentException to be thrown");
        verify(orderRepository, times(1)).getOrderById(orderId);
    }

    @Test
    void testDeleteOrderById_Success() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.getOrderById(orderId)).thenReturn(order);
        doNothing().when(orderRepository).deleteOrderById(orderId);

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteOrderById(orderId);
    }

    @Test
    void testDeleteOrderById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(null), "Expected IllegalArgumentException to be thrown");
        verify(orderRepository, times(0)).deleteOrderById(null);
    }

    @Test
    void testDeleteOrderById_NotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.getOrderById(orderId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrderById(orderId), "Expected IllegalArgumentException to be thrown");
        verify(orderRepository, times(0)).deleteOrderById(orderId);
    }
    // ------------------------ ProductService Tests -------------------------

    @Test
    void testAddProduct_Success() {
        Product product = new Product();
        when(productRepository.addProduct(product)).thenReturn(product);

        Product addedProduct = productService.addProduct(product);

        assertNotNull(addedProduct);
        assertEquals(product, addedProduct);
        verify(productRepository, times(1)).addProduct(product);
    }
    @Test
    void testAddProduct_NullProduct() {
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(null), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).addProduct(null);
    }

    @Test
    void testAddProduct_DuplicateProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Test Product");
        product.setPrice(100.0);

        when(productRepository.getProductById(product.getId())).thenReturn(null);
        when(productRepository.addProduct(product)).thenReturn(product);
        productService.addProduct(product);

        Product duplicateProduct = new Product();
        duplicateProduct.setId(product.getId());
        duplicateProduct.setName("Duplicate Product");
        duplicateProduct.setPrice(150.0);

        when(productRepository.getProductById(duplicateProduct.getId())).thenReturn(product);

        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(duplicateProduct), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).addProduct(duplicateProduct);
    }

    // Get Products
    @Test
    void testGetProducts_Success() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.getProducts()).thenReturn(products);

        ArrayList<Product> retrievedProducts = productService.getProducts();

        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProducts_EmptyList() {
        when(productRepository.getProducts()).thenReturn(new ArrayList<>());

        ArrayList<Product> retrievedProducts = productService.getProducts();

        assertNotNull(retrievedProducts);
        assertEquals(0, retrievedProducts.size());
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProducts_RepositoryReturnsNull() {
        when(productRepository.getProducts()).thenReturn(null);

        ArrayList<Product> retrievedProducts = productService.getProducts();

        assertNull(retrievedProducts);
        verify(productRepository, times(1)).getProducts();
    }

    @Test
    void testGetProductById_Success() {
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
    void testGetProductById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(null), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).getProductById(null);
    }

    @Test
    void testGetProductById_NotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.getProductById(productId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(productId), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(1)).getProductById(productId);
    }


    @Test
    void testUpdateProduct_Success() {
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
    void testUpdateProduct_NullId() {
        String newName = "Updated Product";
        double newPrice = 20.0;

        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(null, newName, newPrice), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).updateProduct(null, newName, newPrice);
    }

    @Test
    void testUpdateProduct_InvalidPrice() {
        UUID productId = UUID.randomUUID();
        String newName = "Updated Product";
        double newPrice = -20.0;

        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productId, newName, newPrice), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).updateProduct(productId, newName, newPrice);
    }
    @Test
    void testApplyDiscount_Success() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);
        product.setPrice(100.0);

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        ArrayList<UUID> productIds = new ArrayList<>();
        productIds.add(productId);

        double discount = 10.0;

        when(productRepository.getProducts()).thenReturn(products);
        when(productRepository.getProductById(productId)).thenReturn(product);

        productService.applyDiscount(discount, productIds);

        assertEquals(90.0, product.getPrice()); // Check discounted price
        verify(productRepository, times(1)).applyDiscount(discount, productIds);
    }

    @Test
    void testApplyDiscount_NullProductIds() {
        double discount = 10.0;

        assertThrows(IllegalArgumentException.class, () -> productService.applyDiscount(discount, null), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).applyDiscount(discount, null);
    }

    @Test
    void testApplyDiscount_InvalidDiscount() {
        ArrayList<UUID> productIds = new ArrayList<>();
        productIds.add(UUID.randomUUID());
        double discount = -10.0;

        assertThrows(IllegalArgumentException.class, () -> productService.applyDiscount(discount, productIds), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).applyDiscount(discount, productIds);
    }


    @Test
    void testDeleteProductById_Success() {
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setId(productId);

        when(productRepository.getProductById(productId)).thenReturn(product).thenReturn(null);
        doNothing().when(productRepository).deleteProductById(productId);

        productService.deleteProductById(productId);

        verify(productRepository, times(1)).deleteProductById(productId);
        assertThrows(IllegalArgumentException.class, () -> productService.getProductById(productId), "Expected IllegalArgumentException to be thrown");
    }

    @Test
    void testDeleteProductById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProductById(null), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).deleteProductById(null);
    }

    @Test
    void testDeleteProductById_NotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.getProductById(productId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> productService.deleteProductById(productId), "Expected IllegalArgumentException to be thrown");
        verify(productRepository, times(0)).deleteProductById(productId);
    }

    // ------------------------ UserService Tests -------------------------

    @Test
    void testAddUser_Success() {
        User user = new User();
        when(userRepository.addUser(user)).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertNotNull(addedUser);
        assertEquals(user, addedUser);
        verify(userRepository, times(1)).addUser(user);
    }

    @Test
    void testAddUser_NullUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(null), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).addUser(null);
    }

    @Test
    void testAddUser_UserWithExistingId() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setId(userId);
        when(userRepository.getUserById(userId)).thenReturn(existingUser);

        User newUser = new User();
        newUser.setId(userId);

        assertThrows(IllegalArgumentException.class, () -> userService.addUser(newUser), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).addUser(newUser);
    }

    @Test
    void testGetUsers_Success() {
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
    void testGetUsers_EmptyList() {
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        ArrayList<User> retrievedUsers = userService.getUsers();

        assertNotNull(retrievedUsers);
        assertEquals(0, retrievedUsers.size());
        verify(userRepository, times(1)).getUsers();
    }

    @Test
    void testGetUsers_RepositoryReturnsNull() {
        when(userRepository.getUsers()).thenReturn(null);

        ArrayList<User> retrievedUsers = userService.getUsers();

        assertNull(retrievedUsers);
        verify(userRepository, times(1)).getUsers();
    }

    @Test
    void testGetUserById_Success() {
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
    void testGetUserById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(null), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).getUserById(null);
    }


    @Test
    void testGetOrdersByUserId_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        user.setOrders(orders);
        when(userRepository.getUserById(userId)).thenReturn(user);

        List<Order> retrievedOrders = userService.getOrdersByUserId(userId);

        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
        verify(userRepository, times(1)).getUserById(userId);
    }

    @Test
    void testGetOrdersByUserId_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.getOrdersByUserId(null), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).getUserById(null);
    }

    @Test
    void testGetOrdersByUserId_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.getUserById(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.getOrdersByUserId(userId), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(1)).getUserById(userId);
    }

    @Test
    void testAddOrderToUser_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Cart cart = new Cart();
        cart.setUserId(userId);
        Product product1 = new Product();
        product1.setPrice(10.0);
        Product product2 = new Product();
        product2.setPrice(20.0);
        cart.setProducts(List.of(product1, product2));

        when(userRepository.getUserById(userId)).thenReturn(user);
        when(cartService.getCartByUserId(userId)).thenReturn(cart);

        userService.addOrderToUser(userId);

        verify(userRepository, times(1)).addOrderToUser(eq(userId), any(Order.class));
        verify(cartService, times(1)).getCartByUserId(userId);
        verify(cartRepository, times(1)).updateCart(cart);
    }

    @Test
    void testAddOrderToUser_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.addOrderToUser(null), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).getUserById(null);
    }

    @Test
    void testAddOrderToUser_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.getUserById(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.addOrderToUser(userId), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(1)).getUserById(userId);
    }

    @Test
    void testEmptyCart_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Cart cart = new Cart();
        cart.setUserId(userId);
        when(cartRepository.getCartByUserId(userId)).thenReturn(cart);

        userService.emptyCart(userId);

        assertTrue(cart.getProducts().isEmpty());
        verify(cartRepository, times(1)).updateCart(cart);
    }

    @Test
    void testEmptyCart_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.emptyCart(null), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(0)).getCartByUserId(null);
    }

    @Test
    void testEmptyCart_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(cartRepository.getCartByUserId(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.emptyCart(userId), "Expected IllegalArgumentException to be thrown");
        verify(cartRepository, times(1)).getCartByUserId(userId);
    }


    @Test
    void testRemoveOrderFromUser_Success() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        Order order = new Order();
        order.setId(orderId);
        user.getOrders().add(order);
        when(userRepository.getUserById(userId)).thenReturn(user);

        userService.removeOrderFromUser(userId, orderId);

        assertFalse(user.getOrders().contains(order));
        verify(userRepository, times(1)).updateUser(user);
    }

    @Test
    void testRemoveOrderFromUser_NullUserId() {
        UUID orderId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> userService.removeOrderFromUser(null, orderId), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).getUserById(null);
    }

    @Test
    void testRemoveOrderFromUser_NullOrderId() {
        UUID userId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> userService.removeOrderFromUser(userId, null), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).getUserById(userId);
    }

    @Test
    void testDeleteUserById_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        when(userRepository.getUserById(userId)).thenReturn(user);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteUserById(userId);
        //assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId), "Expected IllegalArgumentException to be thrown");
    }
    @Test
    void testDeleteUserById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(null), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).deleteUserById(null);
    }

    @Test
    void testDeleteUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.getUserById(userId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(userId), "Expected IllegalArgumentException to be thrown");
        verify(userRepository, times(0)).deleteUserById(userId);
    }
}