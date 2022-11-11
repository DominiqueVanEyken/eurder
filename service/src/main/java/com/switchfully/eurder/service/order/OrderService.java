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
import com.switchfully.eurder.service.item.ItemMapper;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final ItemGroupMapper itemGroupMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        orderMapper = new OrderMapper();
        this.itemGroupMapper = new ItemGroupMapper();
    }

    public OrderDTO createOrder(String customerID, CreateOrderDTO createOrderDTO) {
        List<ItemGroup> itemGroups = new ArrayList<>();
        for (CreateItemGroupDTO itemGroupDTO : createOrderDTO.getOrderList()) {
            Item item = itemRepository.getItemByID(itemGroupDTO.getItemID())
                    .orElseThrow(() -> new NoSuchElementException("Item with ID " + itemGroupDTO.getItemID() + " could not be found"));
            itemGroups.add(itemGroupMapper.mapDTOToItemGroup(item, itemGroupDTO.getAmount()));

        }
        Order order = orderMapper.mapDTOToOrder(createOrderDTO.getCustomerID(), itemGroups);

        orderRepository.createOrder(order);
        Optional<Order> returningOrder = orderRepository.findOrderByID(order.getOrderID());
        return orderMapper.mapOrderToDTO(returningOrder
                .orElseThrow(() -> new NoSuchElementException("The created order with ID " + order.getCustomerID() + "could not be found")));
    }

    protected Order getOrderByOrderID(String orderID) {
        Optional<Order> order = orderRepository.findOrderByID(orderID);
        return order
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + orderID + " does not exist"));
    }

    public OrderDTO reOrderByOrderID(String customerID, String orderID, String username) {
        Order orderToReorder = getOrderByOrderID(orderID);
        validateOrderIDBelongsToCustomer(customerID, orderToReorder, username);
        //TODO: create mapping?
        //TODO: create itemGroupBuilder

        List<ItemGroup> itemGroupsToReorder = new ArrayList<>();
        for (ItemGroup itemGroup : orderToReorder.getOrderList()) {
            Item item = itemRepository.getItemByID(itemGroup.getItemID())
                    .orElseThrow(() -> new NoSuchElementException("Item with ID " + itemGroup.getItemID() + " could not be found"));
            int amount = itemGroup.getAmount();
            itemGroupsToReorder.add(new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice()));
        }
        Order order = new Order(orderToReorder.getCustomerID(), itemGroupsToReorder);
        // TODO: Move to Order?
        Order.calculateTotalPrice(order, itemRepository);
        orderRepository.createOrder(order);
        return orderMapper.mapOrderToDTO(order);
    }

    public void validateOrderIDBelongsToCustomer(String customerID, Order order, String username) {
        if (!order.getCustomerID().equals(customerID)) {
            throw new UnauthorizedException();
        }
        Optional<Customer> customer = customerRepository.findCustomerByID(customerID);
        customer.orElseThrow(() -> new NoSuchElementException("Customer could not be found"));
        if (!customer.get().getEmailAddress().equals(username)) {
            throw new UnauthorizedException();
        }
    }
}
