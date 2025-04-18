package com.satya.ecom.service;

import com.satya.ecom.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order placeOrder();
    List<Order> getMyOrders();
}
