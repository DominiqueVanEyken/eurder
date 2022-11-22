package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest {
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private final Item item1 = new Item("name1", "description", new Price(1.1), 100);
    private final Item item2 = new Item("name2", "description", new Price(2.2), 200);
    private String customerID;
    private List<ItemGroup> orderList;
    private Order order;

    @BeforeEach
    void createAndFillRepository() {
        itemRepository = new ItemRepository();
        orderRepository = new OrderRepository(itemRepository, customerRepository);
        itemRepository.addItem(item1);
        itemRepository.addItem(item2);
        orderList = List.of(
                new ItemGroup(item1.getItemID(), item1.getName(), 1, item1.getShippingDateForAmount(1), item1.getPrice()),
                new ItemGroup(item2.getItemID(), item2.getName(), 2, item2.getShippingDateForAmount(2), item2.getPrice())
        );
        customerID = customerRepository.findAll().stream().toList().get(0).getCustomerID();
        order = new Order(customerID, orderList);
    }

    @Test
    void addingAnOrderToTheRepository() {
        int before = orderRepository.getOrders().size();
        orderRepository.createOrder(order);
        int after = orderRepository.getOrders().size();

        assertThat(before == after - 1).isTrue();
    }

    @Test
    void gettingAnOrderByOrderID_givenValidOrderID() {
        orderRepository.createOrder(order);
        Order result = orderRepository.findOrderByID(order.getOrderID()).get();

        assertThat(result).isEqualTo(order);
    }

    @Test
    void gettingOrdersByCustomerID() {
        orderRepository.createOrder(order);
        List<Order> orders = orderRepository.getOrdersByCustomerID(customerID);

        assertThat(orders).isNotNull();
        assertThat(orders).isEqualTo(List.of(order));
    }

    @Test
    void itemGroupShipsToday() {
        orderRepository.createOrder(order);
        order.getOrderList().get(0).setShippingDate(LocalDate.now());
        List<ItemGroupShippingReport> itemGroupShippingReports = orderRepository.getItemGroupsShippingToday();

        assertThat(itemGroupShippingReports).isNotNull();
        assertThat(itemGroupShippingReports.size()).isEqualTo(1);
    }

}