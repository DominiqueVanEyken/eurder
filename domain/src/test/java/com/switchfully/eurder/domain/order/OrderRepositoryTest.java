package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderRepositoryTest {

    private ItemRepository itemRepository;
    private OrderRepository orderRepository;
    private final Item item1 = new Item("name1", "description", new Price(1.1), 100);
    private final Item item2 = new Item("name2", "description", new Price(2.2), 200);
    private final String customerID = "CID20221001";
    private List<ItemGroup> orderList;
    private Order order;

    @BeforeEach
    void createAndFillRepository() {
        itemRepository = new ItemRepository();
        orderRepository = new OrderRepository(itemRepository);
        itemRepository.addItem(item1);
        itemRepository.addItem(item2);
        orderList = List.of(
                new ItemGroup(item1.getItemID(), 1),
                new ItemGroup(item2.getItemID(), 2)
        );
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
        Order result = orderRepository.findOrderByID(order.getOrderID());

        assertThat(result).isEqualTo(order);
    }

    @Test
    void gettingOrderByOrderID_givenInvalidOrderID() {
        String orderID = "invalidID";
        assertThatThrownBy(() -> orderRepository.findOrderByID(orderID))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Order with ID " + orderID + " does not exist");
    }
}