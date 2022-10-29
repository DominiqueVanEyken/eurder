package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.item.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private final Map<String, Order> orderRepository;
    private final ItemRepository itemRepository;

    public OrderRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        orderRepository = new HashMap<>();
    }

    public Order createOrder(Order order) {
        for (ItemGroup itemGroup : order.getOrderList()) {
            itemGroup.setShippingDateAndPrice(itemRepository.getItemByID(itemGroup.getItemID()));
        }
        order.calculateTotalPrice();
        orderRepository.put(order.getOrderID(), order);
        log.info("Created ".concat(order.toString()));
        return order;
    }
}
