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
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderServiceTest {
    private OrderService orderService;
    private ItemRepository itemRepository;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private final Item item1 = new Item("name1", "description", new Price(1.1), 100);
    private final Item item2 = new Item("name2", "description", new Price(2.2), 200);
    private String customerID;
    private List<ItemGroup> orderList;
    private Order order;

    @BeforeEach
    void createAndFillRepository() {
        itemRepository = new ItemRepository();
        customerRepository = new CustomerRepository();
        orderRepository = new OrderRepository(itemRepository, customerRepository);
        orderService = new OrderService(orderRepository, customerRepository, itemRepository);
        itemRepository.addItem(item1);
        itemRepository.addItem(item2);
        orderList = List.of(
                new ItemGroup(item1.getItemID(), 1),
                new ItemGroup(item2.getItemID(), 2)
        );
        customerID = customerRepository.getAllCustomers().stream().toList().get(0).getCustomerID();
        order = new Order(customerID, orderList);
    }

    @Test
    void validateOrderIDBelongsToCustomer_givenInvalidCustomerID() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();

        assertThatThrownBy(() -> orderService.validateOrderIDBelongsToCustomer("someID", order, customer.getEmailAddress()))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("User does not have authorized access");
    }

    @Test
    void validateOrderIDBelongsToCustomer_givenInvalidEmail() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();

        assertThatThrownBy(() -> orderService.validateOrderIDBelongsToCustomer(customer.getCustomerID(), order, "invalid@email.be"))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("User does not have authorized access");
    }

    @Test
    void reorderOrderByID_givenValidData() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();

        OrderDTO result = orderService.reOrderByOrderID(customer.getCustomerID(), order.getOrderID(), customer.getEmailAddress());

        assertThat(result).isNotNull();
        assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
        assertThat(result.getCustomerID()).isEqualTo(customer.getCustomerID());
        assertThat(result.getOrderList().size()).isEqualTo(order.getOrderList().size());
    }

    @Test
    void reorderOrderByID_givenInvalidCostumerData() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();

        assertThatThrownBy(() -> orderService.reOrderByOrderID("invalidID", order.getOrderID(), "invalid@email.be"))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("User does not have authorized access");
    }

    @Test
    void reorderOrderByID_givenInvalidOrderID() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID()).get();

        assertThatThrownBy(() -> orderService.reOrderByOrderID(customer.getCustomerID(), "invalidOrderID", customer.getEmailAddress()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Order with ID invalidOrderID does not exist");

    }
}