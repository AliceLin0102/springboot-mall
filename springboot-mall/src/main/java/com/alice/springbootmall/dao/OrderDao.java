package com.alice.springbootmall.dao;

import com.alice.springbootmall.dto.CreateOrderRequest;
import com.alice.springbootmall.dto.OrderQueryParams;
import com.alice.springbootmall.model.Order;
import com.alice.springbootmall.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
