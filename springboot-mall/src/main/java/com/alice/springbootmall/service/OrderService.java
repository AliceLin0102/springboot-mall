package com.alice.springbootmall.service;

import com.alice.springbootmall.dto.CreateOrderRequest;
import com.alice.springbootmall.dto.OrderQueryParams;
import com.alice.springbootmall.model.Order;

import javax.swing.*;
import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId,CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
