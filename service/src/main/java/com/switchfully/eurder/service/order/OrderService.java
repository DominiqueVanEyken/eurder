package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.customer.Customer;
import com.switchfully.eurder.domain.customer.CustomerRepository;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupRepository;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderMapper orderMapper;
    private final ItemGroupMapper itemGroupMapper;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final ItemGroupRepository itemGroupRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ItemRepository itemRepository, ItemGroupRepository itemGroupRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.itemGroupRepository = itemGroupRepository;
        orderMapper = new OrderMapper();
        itemGroupMapper = new ItemGroupMapper();
    }

    public OrderDTO createOrder(String customerID, CreateOrderDTO createOrderDTO) {
        List<ItemGroup> itemGroups = new ArrayList<>();
        Order order = new Order(customerID);
        for (CreateItemGroupDTO itemGroupDTO : createOrderDTO.getOrderList()) {
            String itemID = itemGroupDTO.getItemID();
            int amount = itemGroupDTO.getAmount();
            mapItemToItemGroupAndReduceStock(order, itemGroups, itemID, amount);
        }
        order.updatePrice(itemGroups);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(itemGroupRepository.findByOrder(order));
        orderRepository.save(order);
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
    }

    protected Order getOrderByOrderID(String orderID) {
        Optional<Order> order = orderRepository.findById(orderID);
        return order
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + orderID + " does not exist"));
    }

    public OrderDTO reOrderByOrderID(String customerID, String orderID) {
        Order orderToReorder = getOrderByOrderID(orderID);
        List<ItemGroup> itemGroupsOldOrder = itemGroupRepository.findByOrder(orderToReorder);
        Order order = new Order(customerID);
        List<ItemGroup> itemGroupsToReorder = new ArrayList<>();
        for (ItemGroup itemGroup : itemGroupsOldOrder) {
            String itemID = itemGroup.getItemID();
            int amount = itemGroup.getAmount();
            mapItemToItemGroupAndReduceStock(order, itemGroupsToReorder, itemID, amount);
        }
        order.updatePrice(itemGroupsToReorder);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(itemGroupRepository.findByOrder(order));
        orderRepository.save(order);
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
    }

    public void validateOrderIDBelongsToCustomer(String customerID, Order order, String username) {
        if (!order.getCustomerID().equals(customerID)) {
            throw new UnauthorizedException();
        }
        Optional<Customer> customer = customerRepository.findById(customerID);
        customer.orElseThrow(() -> new NoSuchElementException("Customer could not be found"));
        if (!customer.get().getEmailAddress().equals(username)) {
            throw new UnauthorizedException();
        }
    }

    private void mapItemToItemGroupAndReduceStock(Order order, List<ItemGroup> itemGroups, String itemID, int amount) {
        Item item = itemRepository.findById(itemID)
                .orElseThrow(() -> new NoSuchElementException("Item with ID " + itemID + " could not be found"));
        ItemGroup itemGroup = itemGroupMapper.mapItemToItemGroup(order, item, amount);
        itemGroupRepository.save(itemGroup);
        itemGroups.add(itemGroup);
        item.reduceStockByAmount(amount);
    }
}
