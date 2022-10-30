package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private final Map<String, Order> orderRepository;
    private final ItemRepository itemRepository;

    public OrderRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        orderRepository = new HashMap<>();
    }

    public Collection<Order> getOrders() {
        return orderRepository.values();
    }

    public void createOrder(Order order) {
        Order.calculateTotalPrice(order, itemRepository);
        reduceStock(order.getOrderList());
        orderRepository.put(order.getOrderID(), order);
        log.info("Created ".concat(order.toString()));
    }

    public Order findOrderByID(String orderID) {
        if (!orderRepository.containsKey(orderID)) {
            throw new NoSuchElementException("Order with ID " + orderID + " does not exist");
        }
        return orderRepository.get(orderID);
    }

    public void reduceStock(List<ItemGroup> itemGroupList) {
        itemGroupList.forEach(itemGroup -> itemRepository.reduceStockForItemByAmount(itemGroup.getItemID(), itemGroup.getAmount()));
    }

    public List<Order> getOrdersByCustomerID(String customerID) {
        return orderRepository.values().stream()
                .filter(order -> order.getCustomerID().equals(customerID))
                .toList();
    }
}
