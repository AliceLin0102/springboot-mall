package com.alice.springbootmall.dao;

import com.alice.springbootmall.dto.CreateOrderRequest;
import com.alice.springbootmall.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
