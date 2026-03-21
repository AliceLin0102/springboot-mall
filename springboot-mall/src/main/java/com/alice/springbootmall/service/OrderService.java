package com.alice.springbootmall.service;

import com.alice.springbootmall.dto.CreateOrderRequest;
import com.alice.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId,CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
