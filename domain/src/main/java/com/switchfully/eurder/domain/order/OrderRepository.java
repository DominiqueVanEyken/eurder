package com.switchfully.eurder.domain.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private final Map<String, Order> orderRepository;

    public OrderRepository() {
        orderRepository = new HashMap<>();
    }

    public void createOrder(Order order) {
        orderRepository.put(order.getOrderID(), order);
        log.info("Created ".concat(order.toString()));
    }
}
