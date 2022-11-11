package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    private ItemRepository itemRepository;
    private final Item item1 = new Item("name1", "description", new Price(1.1), 100);
    private final Item item2 = new Item("name2", "description", new Price(1.1), 100);
    private final String customerID = "CID20221005";
    private final LocalDate orderDate = LocalDate.now();
    private final List<ItemGroup> orderList = List.of(
            new ItemGroup(item1.getItemID(), item1.getName(), 3, item1.getShippingDateForAmount(3),item1.getPrice()),
            new ItemGroup(item2.getItemID(), item2.getName(), 3, item2.getShippingDateForAmount(3),item2.getPrice())
    );

    @BeforeEach
    void createAndFillRepository() {
        itemRepository = new ItemRepository();
        itemRepository.addItem(item1);
        itemRepository.addItem(item2);
    }

    @Nested
    class givenValidData {
        @Test
        void creatingAnOrder() {
            Order order = new Order(customerID, orderList);
            Order.calculateTotalPrice(order, itemRepository);

            assertThat(order).isNotNull();
            assertThat(order.getOrderID()).isNotNull();
            assertThat(order.getCustomerID()).isEqualTo(customerID);
            assertThat(order.getOrderDate()).isEqualTo(orderDate);
            assertThat(order.getOrderList()).isEqualTo(orderList);
            assertThat(order.getTotalPrice().toString()).isEqualTo(new Price(6.6).toString());
            assertThat(order.toString()).isEqualTo(String.format("Order{orderID=%s, customerID=%s, orderDate=%s}", order.getOrderID(), customerID, orderDate));
            assertThat(order.equals(new Order(customerID, orderList))).isFalse();
            assertThat(order.hashCode()).isNotEqualTo(new Order(customerID, orderList).hashCode());
        }
    }

}