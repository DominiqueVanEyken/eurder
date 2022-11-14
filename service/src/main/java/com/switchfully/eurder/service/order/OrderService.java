package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
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
        itemGroupMapper = new ItemGroupMapper();
    }

    public OrderDTO createOrder(String customerID, CreateOrderDTO createOrderDTO) {
        List<ItemGroup> itemGroups = new ArrayList<>();
        for (CreateItemGroupDTO itemGroupDTO : createOrderDTO.getOrderList()) {
            String itemID = itemGroupDTO.getItemID();
            int amount = itemGroupDTO.getAmount();
            mapItemToItemGroupAndReduceStock(itemGroups, itemID, amount);
        }
        Order order = orderMapper.mapDTOToOrder(customerID, itemGroups);
        orderRepository.createOrder(order);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(order.getOrderList());
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
    }

    protected Order getOrderByOrderID(String orderID) {
        Optional<Order> order = orderRepository.findOrderByID(orderID);
        return order
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + orderID + " does not exist"));
    }

    public OrderDTO reOrderByOrderID(String customerID, String orderID) {
        Order orderToReorder = getOrderByOrderID(orderID);
        List<ItemGroup> itemGroupsToReorder = new ArrayList<>();
        for (ItemGroup itemGroup : orderToReorder.getOrderList()) {
            String itemID = itemGroup.getItemID();
            int amount = itemGroup.getAmount();
            mapItemToItemGroupAndReduceStock(itemGroupsToReorder, itemID, amount);
        }
        Order order = orderMapper.mapDTOToOrder(customerID, itemGroupsToReorder);
        orderRepository.createOrder(order);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(order.getOrderList());
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
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

    private void mapItemToItemGroupAndReduceStock(List<ItemGroup> itemGroups, String itemID, int amount) {
        Item item = itemRepository.getItemByID(itemID)
                .orElseThrow(() -> new NoSuchElementException("Item with ID " + itemID + " could not be found"));
        itemGroups.add(itemGroupMapper.mapItemToItemGroup(item, amount));
        item.reduceStockByAmount(amount);
    }
}
