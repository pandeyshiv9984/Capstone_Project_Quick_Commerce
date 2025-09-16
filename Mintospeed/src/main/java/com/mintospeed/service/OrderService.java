package com.mintospeed.service;

import com.mintospeed.dto.CreateOrderRequest;
import com.mintospeed.model.Order;
import com.mintospeed.model.OrderItem;
import com.mintospeed.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        return order;
//        order.setUserId(request.getUserId());
//        order.setAddressId(request.getAddressId());
//        order.setCouponId(request.getCouponId());
//        order.setTotalAmount(request.getTotalAmount());
//        order.setDiscount(request.getDiscount());
//        order.setStatus("PLACED");
//        order.setEtaMinutes(30);
//        order.setCreatedAt(LocalDateTime.now());
//
//        List<OrderItem> orderItems = request.getOrderItems().stream()
//                .map(itemDto -> {
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setOrder(order);
//                    orderItem.setItemId(itemDto.getItemId());
//                    orderItem.setQuantity(itemDto.getQuantity());
//                    orderItem.setPrice(itemDto.getPrice());
//                    return orderItem;
//                })
//                .collect(Collectors.toList());
//
//        order.setOrderItems(orderItems);
//        return orderRepository.save(order);
    }
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
