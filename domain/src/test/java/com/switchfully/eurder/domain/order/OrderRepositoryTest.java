package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderRepositoryTest {

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
        List<ItemGroupShipping> itemGroupShippings = orderRepository.getShippingReportPerItemGroup();

        assertThat(itemGroupShippings).isNotNull();
        assertThat(itemGroupShippings.size()).isEqualTo(1);
    }

    @Test
    void validateOrderIDBelongsToCustomer_givenValidData() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID());

        boolean result = orderRepository.validateOrderIDBelongsToCustomer(customer.getCustomerID(), order, customer.getEmailAddress());

        assertThat(result).isTrue();
    }

    @Test
    void validateOrderIDBelongsToCustomer_givenInvalidCustomerID() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID());

        boolean result = orderRepository.validateOrderIDBelongsToCustomer("someID", order, customer.getEmailAddress());

        assertThat(result).isFalse();
    }

    @Test
    void validateOrderIDBelongsToCustomer_givenInvalidEmail() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID());

        boolean result = orderRepository.validateOrderIDBelongsToCustomer(customer.getCustomerID(), order, "invalid@email.be");

        assertThat(result).isFalse();
    }

    @Test
    void reorderOrderByID_givenValidData() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID());

        Order result = orderRepository.reorderOrderByID(customer.getCustomerID(), order.getOrderID(), customer.getEmailAddress());

        assertThat(result).isNotNull();
        assertThat(result.getOrderDate()).isEqualTo(LocalDate.now());
        assertThat(result.getCustomerID()).isEqualTo(customer.getCustomerID());
        assertThat(result.getOrderList().size()).isEqualTo(order.getOrderList().size());
    }

    @Test
    void reorderOrderByID_givenInvalidCostumerData() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID());

        assertThatThrownBy(() -> orderRepository.reorderOrderByID("invalidID", order.getOrderID(), "invalid@email.be"))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("User does not have authorized access");
    }

    @Test
    void reorderOrderByID_givenInvalidOrderID() {
        orderRepository.createOrder(order);
        Customer customer = customerRepository.findCustomerByID(order.getCustomerID());

        assertThatThrownBy(() -> orderRepository.reorderOrderByID(customer.getCustomerID(), "invalidOrderID", customer.getEmailAddress()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Order with ID invalidOrderID does not exist");

    }
}