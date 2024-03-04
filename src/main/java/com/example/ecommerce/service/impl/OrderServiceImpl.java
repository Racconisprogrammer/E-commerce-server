package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.exception.OrderException;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderItemService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;
    private final AddressRepository addressRepository;


    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createdOrderItem =

        }
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return null;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order dilveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> getAllOrders() throws OrderException {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
