package com.satya.ecom.controller;

import com.satya.ecom.model.Order;
import com.satya.ecom.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> placeOrder(){
        return ResponseEntity.ok(orderService.placeOrder());
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(){
        return ResponseEntity.ok(orderService.getMyOrders());
    }
}
