package com.satya.ecom.service.impl;

import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.model.*;
import com.satya.ecom.repository.CartRepo;
import com.satya.ecom.repository.OrderRepo;
import com.satya.ecom.repository.UserRepo;
import com.satya.ecom.service.AuthService;
import com.satya.ecom.service.OrderService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final AuthService authService;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final CartRepo cartRepo;

    public OrderServiceImpl(AuthService authService, OrderRepo orderRepo, UserRepo userRepo, CartRepo cartRepo) {
        this.authService = authService;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
    }

    @Override
    public Order placeOrder() {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userResponseDto.email()));
        Order order = new Order();
        Cart cart = user.getCart();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem: cart.getCartItemList()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItemList.add(orderItem);
        }
        order.setItems(orderItemList);
        order.setStatus(Status.CONFIRMED);
        order.setUser(user);
        order.setTotalAmount(cart.getTotalCartAmount());
        cart.getCartItemList().clear();
        cart.setTotalCartAmount(0.0);
        cartRepo.save(cart);
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getMyOrders() {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + userResponseDto.email()));
        return user.getOrder();
    }
}
