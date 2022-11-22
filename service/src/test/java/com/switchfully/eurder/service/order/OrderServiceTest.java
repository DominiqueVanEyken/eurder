package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest {
    private OrderService orderService;
    @Autowired
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
        orderRepository = new OrderRepository(itemRepository, customerRepository);
        orderService = new OrderService(orderRepository, customerRepository, itemRepository);
        itemRepository.save(item1);
        itemRepository.save(item2);
        orderList = List.of(
                new ItemGroup(item1.getItemID(),item1.getName(), 1, item1.getShippingDateForAmount(1), item1.getPrice()),
                new ItemGroup(item2.getItemID(),item2.getName(), 2, item2.getShippingDateForAmount(2), item2.getPrice())
        );
        customerID = customerRepository.findAll().stream().toList().get(0).getCustomerID();
        order = new Order(customerID, orderList);
    }

    @Nested
    class ValidateCustomerIDBelongsToCustomer {
        @Test
        void validateOrderIDBelongsToCustomer_givenInvalidCustomerID() {
            orderRepository.createOrder(order);
            Customer customer = customerRepository.findById(order.getCustomerID()).get();

            assertThatThrownBy(() -> orderService.validateOrderIDBelongsToCustomer("someID", order, customer.getEmailAddress()))
                    .isInstanceOf(UnauthorizedException.class)
                    .hasMessageContaining("User does not have authorized access");
        }

        @Test
        void validateOrderIDBelongsToCustomer_givenInvalidEmail() {
            orderRepository.createOrder(order);
            Customer customer = customerRepository.findById(order.getCustomerID()).get();

            assertThatThrownBy(() -> orderService.validateOrderIDBelongsToCustomer(customer.getCustomerID(), order, "invalid@email.be"))
                    .isInstanceOf(UnauthorizedException.class)
                    .hasMessageContaining("User does not have authorized access");
        }
    }

    @Nested
    class GettingOrderByOrderID {
        @Test
        void gettingOrderByOrderID_givenInvalidOrderID() {
            String orderID = "invalidID";
            assertThatThrownBy(() -> orderService.getOrderByOrderID(orderID))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining("Order with ID " + orderID + " does not exist");
        }
    }

    @Nested
    class ReorderByOrderID {
        @Test
        void reorderOrderByID_givenValidData() {
            orderRepository.createOrder(order);
            Customer customer = customerRepository.findById(order.getCustomerID()).get();

            OrderDTO result = orderService.reOrderByOrderID(customer.getCustomerID(), order.getOrderID());

            assertThat(result).isNotNull();
            assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
            assertThat(result.getCustomerID()).isEqualTo(customer.getCustomerID());
            assertThat(result.getOrderList().size()).isEqualTo(order.getOrderList().size());
        }

        @Test
        void reorderOrderByID_givenInvalidOrderID() {
            orderRepository.createOrder(order);
            Customer customer = customerRepository.findById(order.getCustomerID()).get();

            assertThatThrownBy(() -> orderService.reOrderByOrderID(customer.getCustomerID(), "invalidOrderID"))
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessageContaining("Order with ID invalidOrderID does not exist");

        }
    }
}
