package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.customer.Customer;
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
        List<String> itemIDs = itemRepository.getAllItemsFromRepository().stream()
                .map(Item::getItemID)
                .limit(3)
                .toList();
        ItemGroup itemGroup1 = new ItemGroup(itemIDs.get(0), 1);
        ItemGroup itemGroup2 = new ItemGroup(itemIDs.get(1), 2);
        ItemGroup itemGroup3 = new ItemGroup(itemIDs.get(2), 3);
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

    public Optional<Order> findOrderByID(String orderID) {
        return Optional.ofNullable(orderRepository.get(orderID));
    }

    // TODO: Move to OrderService or ItemService
    public void reduceStock(List<ItemGroup> itemGroupList) {
        itemGroupList.forEach(itemGroup -> itemRepository.reduceStockForItemByAmount(itemGroup.getItemID(), itemGroup.getAmount()));
        log.debug("Reducing stock");
    }

    public List<Order> getOrdersByCustomerID(String customerID) {
        return orderRepository.values().stream()
                .filter(order -> order.getCustomerID().equals(customerID))
                .toList();
    }

    // TODO: Move to OrderService
    private boolean itemGroupShipsToday(ItemGroup itemGroup) {
        LocalDate shippingDate = itemGroup.getShippingDate();
        LocalDate today = LocalDate.now();
        return shippingDate.getYear() == today.getYear() && shippingDate.getMonth().equals(today.getMonth()) && shippingDate.getDayOfMonth() == today.getDayOfMonth();
    }

    // TODO: Move to OrderService
    public List<ItemGroupShipping> getShippingReportPerItemGroup() {
        log.info("Generating shipping report");
        List<ItemGroupShipping> itemGroupShippings = new ArrayList<>();
        for (Order order : orderRepository.values()) {
            Optional<Customer> customer;
            for (ItemGroup itemGroup : order.getOrderList()) {
                if (itemGroupShipsToday(itemGroup)) {
                    customer = customerRepository.findCustomerByID(order.getCustomerID());
                    itemGroupShippings.add(new ItemGroupShipping(customer.get().getFullAddress(), itemGroup));
                }
            }
        }
        return itemGroupShippings;
    }
}
