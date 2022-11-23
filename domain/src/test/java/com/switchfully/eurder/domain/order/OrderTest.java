package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    @Autowired
    private ItemRepository itemRepository;
    private final Item item1 = new Item("name1", "description", new Price(1.1), 100);
    private final Item item2 = new Item("name2", "description", new Price(1.1), 100);
    private final String customerID = "CID20221001";
    private final LocalDate orderDate = LocalDate.now();
    private final Order order = new Order(customerID);
    private final List<ItemGroup> orderList = List.of(
            new ItemGroup(order, item1, item1.getName(), 3, item1.getShippingDateForAmount(3),item1.getPrice()),
            new ItemGroup(order, item2, item2.getName(), 3, item2.getShippingDateForAmount(3),item2.getPrice())
    );

    @Nested
    class givenValidData {
        @Test
        void creatingAnOrder() {
            Order order = new Order(customerID);
            order.updatePrice(orderList);

            assertThat(order).isNotNull();
            assertThat(order.getOrderID()).isNotNull();
            assertThat(order.getCustomerID()).isEqualTo(customerID);
            assertThat(order.getOrderDate()).isEqualTo(orderDate);
            assertThat(order.getTotalPrice().toString()).isEqualTo(new Price(6.6).toString());
            assertThat(order.toString()).isEqualTo(String.format("Order{orderID=%s, customerID=%s, orderDate=%s}", order.getOrderID(), customerID, orderDate));
            assertThat(order.equals(new Order(customerID))).isFalse();
        }
    }

}