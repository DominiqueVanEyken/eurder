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
        fillOrderRepository();
    }

    private void fillOrderRepository() {
        ItemGroup itemGroup1 = new ItemGroup("IID20221001", 1);
        ItemGroup itemGroup2 = new ItemGroup("IID20221002", 2);
        ItemGroup itemGroup3 = new ItemGroup("IID20221003", 3);
        Order order1 = new Order("CID20221002", List.of(itemGroup1, itemGroup2, itemGroup3));
        Order.calculateTotalPrice(order1, itemRepository);
        Order order2 = new Order("CID20221003", List.of(itemGroup1, itemGroup2, itemGroup3));
        Order.calculateTotalPrice(order2, itemRepository);
        Order order3 = new Order("CID20221002", List.of(itemGroup1, itemGroup2, itemGroup3));
        Order.calculateTotalPrice(order3, itemRepository);
        orderRepository.put(order1.getOrderID(), order1);
        orderRepository.put(order2.getOrderID(), order2);
        orderRepository.put(order3.getOrderID(), order3);
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
        log.debug("Reducing stock");
    }

    public List<Order> getOrdersByCustomerID(String customerID) {
        return orderRepository.values().stream()
                .filter(order -> order.getCustomerID().equals(customerID))
                .toList();
    }
}
