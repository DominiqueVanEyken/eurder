package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupRepository;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.itemgroup.ItemGroupMapper;
import com.switchfully.eurder.service.itemgroup.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;
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
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemGroupMapper itemGroupMapper;
    private final ItemGroupRepository itemGroupRepository;
    private final ItemService itemService;

    public OrderService(OrderRepository orderRepository, ItemGroupRepository itemGroupRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.itemGroupRepository = itemGroupRepository;
        orderMapper = new OrderMapper();
        itemGroupMapper = new ItemGroupMapper();
    }

    public OrderDTO createOrder(String customerID, CreateOrderDTO createOrderDTO) {
        List<ItemGroup> itemGroups = new ArrayList<>();
        Order order = new Order(customerID);
        for (CreateItemGroupDTO itemGroupDTO : createOrderDTO.getOrderList()) {
            long itemID = itemGroupDTO.getItemID();
            int amount = itemGroupDTO.getAmount();
            mapItemToItemGroupAndReduceStock(order, itemGroups, itemID, amount);
        }
        order.updatePrice(itemGroups);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(itemGroupRepository.findByOrder(order));
        orderRepository.save(order);
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
    }

    protected Order requestOrderByOrderID(long orderID) {
        Optional<Order> order = orderRepository.findById(orderID);
        return order
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + orderID + " does not exist"));
    }

    public OrderDTO reOrderByOrderID(String customerID, long orderID) {
        Order orderToReorder = requestOrderByOrderID(orderID);
        validateOrderIDBelongsToCustomer(customerID, orderToReorder);
        List<ItemGroup> itemGroupsOldOrder = itemGroupRepository.findByOrder(orderToReorder);
        Order order = new Order(customerID);
        List<ItemGroup> itemGroupsToReorder = new ArrayList<>();
        for (ItemGroup itemGroup : itemGroupsOldOrder) {
            long itemID = itemGroup.getItemID();
            int amount = itemGroup.getAmount();
            mapItemToItemGroupAndReduceStock(order, itemGroupsToReorder, itemID, amount);
        }
        order.updatePrice(itemGroupsToReorder);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(itemGroupRepository.findByOrder(order));
        orderRepository.save(order);
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
    }

    public void validateOrderIDBelongsToCustomer(String customerID, Order order) {
        if (!order.getCustomerID().equals(customerID)) {
            throw new UnauthorizedException();
        }
    }

    private void mapItemToItemGroupAndReduceStock(Order order, List<ItemGroup> itemGroups, long itemID, int amount) {
        Item item = itemService.getItemByID(itemID);
        ItemGroup itemGroup = itemGroupMapper.mapItemToItemGroup(order, item, amount);
        itemGroupRepository.save(itemGroup);
        itemGroups.add(itemGroup);
        item.reduceStockByAmount(amount);
    }
}
