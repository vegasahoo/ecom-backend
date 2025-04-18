package com.satya.ecom.service.impl;

import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.model.*;
import com.satya.ecom.repository.OrderRepo;
import com.satya.ecom.repository.UserRepo;
import com.satya.ecom.service.AuthService;
import com.satya.ecom.service.OrderService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final AuthService authService;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    public OrderServiceImpl(AuthService authService, OrderRepo orderRepo, UserRepo userRepo) {
        this.authService = authService;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Order placeOrder() {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email()).get();
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
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getMyOrders() {
        UserResponseDto userResponseDto = authService.getCurrentUser();
        User user = userRepo.findByEmail(userResponseDto.email()).get();
        return user.getOrder();
    }
}
