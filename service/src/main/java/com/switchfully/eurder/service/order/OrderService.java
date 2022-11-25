package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.exceptions.UnauthorizedException;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.domain.order.OrderRepository;
import com.switchfully.eurder.service.itemgroup.ItemGroupService;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemGroupService itemGroupService;

    public OrderService(OrderRepository orderRepository, ItemGroupService itemGroupService) {
        this.orderRepository = orderRepository;
        this.itemGroupService = itemGroupService;
        orderMapper = new OrderMapper();
    }

    public OrderDTO createOrder(String customerID, CreateOrderDTO createOrderDTO) {
        Order order = new Order(customerID);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupService.createItemGroups(createOrderDTO.getOrderList(), order);
        order.updatePrice(new Price(itemGroupDTOS.stream()
                .map(ItemGroupDTO::getTotalPrice)
                .mapToDouble(Price::getPrice)
                .sum()));
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
        List<ItemGroup> itemGroups = itemGroupService.getItemGroupsForOrder(orderToReorder);
        Order order = new Order(customerID);
        List<ItemGroupDTO> itemGroupDTOS = itemGroupService.reorderItemGroups(itemGroups, order);
        order.updatePrice(new Price(itemGroupDTOS.stream()
                .map(ItemGroupDTO::getTotalPrice)
                .mapToDouble(Price::getPrice)
                .sum()));
        orderRepository.save(order);
        return orderMapper.mapOrderToDTO(order, itemGroupDTOS);
    }

    public void validateOrderIDBelongsToCustomer(String customerID, Order order) { //TODO: move to SecurityService
        if (!order.getCustomerID().equals(customerID)) {
            throw new UnauthorizedException();
        }
    }
}
