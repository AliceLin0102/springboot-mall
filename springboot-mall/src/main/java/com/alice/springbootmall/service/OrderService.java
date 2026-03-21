package com.alice.springbootmall.service;

import com.alice.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId,CreateOrderRequest createOrderRequest);
}
