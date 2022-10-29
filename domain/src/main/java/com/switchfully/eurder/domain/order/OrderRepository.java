package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class OrderRepository {
    private final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private final Map<String, Order> orderRepository;
    private final ItemRepository itemRepository;

    public OrderRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        orderRepository = new HashMap<>();
    }

    public void createOrder(Order order) {

        Order.calculateTotalPrice(order, itemRepository);
        orderRepository.put(order.getOrderID(), order);
        log.info("Created ".concat(order.toString()));
    }

    public Order findOrderByID(String orderID) {
        if (!orderRepository.containsKey(orderID)) {
            throw new NoSuchElementException("Order with ID " + orderID + " does not exist");
        }
        return orderRepository.get(orderID);
    }
}
