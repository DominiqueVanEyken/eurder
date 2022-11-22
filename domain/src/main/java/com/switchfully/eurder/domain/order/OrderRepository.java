package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class OrderRepository {
    private final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private final Map<String, Order> orderRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;

    public OrderRepository(ItemRepository itemRepository, CustomerRepository customerRepository) {
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        orderRepository = new HashMap<>();
        fillOrderRepository();
    }

    private void fillOrderRepository() {
        List<Item> items = itemRepository.getAllItemsFromRepository().stream()
                .limit(3)
                .toList();
        ItemGroup itemGroup1 = new ItemGroup(items.get(0).getItemID(), items.get(0).getName(), 1, items.get(0).getShippingDateForAmount(1), items.get(0).getPrice());
        ItemGroup itemGroup2 = new ItemGroup(items.get(1).getItemID(), items.get(1).getName(), 2, items.get(1).getShippingDateForAmount(2), items.get(1).getPrice());
        ItemGroup itemGroup3 = new ItemGroup(items.get(2).getItemID(), items.get(2).getName(), 3, items.get(1).getShippingDateForAmount(3), items.get(2).getPrice());
        Order order1 = new Order("CID20221001", List.of(itemGroup1, itemGroup2, itemGroup3));
        Order order2 = new Order("CID20221003", List.of(itemGroup1, itemGroup2, itemGroup3));
        Order order3 = new Order("CID20221002", List.of(itemGroup1, itemGroup2, itemGroup3));
        orderRepository.put(order1.getOrderID(), order1);
        orderRepository.put(order2.getOrderID(), order2);
        orderRepository.put(order3.getOrderID(), order3);
    }

    public Collection<Order> getOrders() {
        return orderRepository.values();
    }

    public void createOrder(Order order) {
        orderRepository.put(order.getOrderID(), order);
        log.info("Created ".concat(order.toString()));
    }

    public Optional<Order> findOrderByID(String orderID) {
        return Optional.ofNullable(orderRepository.get(orderID));
    }

    public List<Order> getOrdersByCustomerID(String customerID) {
        return orderRepository.values().stream()
                .filter(order -> order.getCustomerID().equals(customerID))
                .toList();
    }

    public List<ItemGroupShippingReport> getItemGroupsShippingToday() {
        log.info("Generating shipping report");
        List<ItemGroupShippingReport> itemGroupShippingReports = new ArrayList<>();
        for (Order order : orderRepository.values()) {
            for (ItemGroup itemGroup : order.getOrderList()) {
                if (doesItemGroupShipsToday(itemGroup)) {
                    customerRepository.findById(order.getCustomerID())
                            .ifPresent(customer -> itemGroupShippingReports.add(
                                            new ItemGroupShippingReport(customer.getEmailAddress(), itemGroup)
                                    )
                            );
                }
            }
        }
        return itemGroupShippingReports;
    }

    private boolean doesItemGroupShipsToday(ItemGroup itemGroup) {
        LocalDate shippingDate = itemGroup.getShippingDate();
        LocalDate today = LocalDate.now();
        return shippingDate.getYear() == today.getYear() && shippingDate.getMonth().equals(today.getMonth()) && shippingDate.getDayOfMonth() == today.getDayOfMonth();
    }
}
