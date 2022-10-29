package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;

public class OrderMapper {
    private final ItemGroupMapper itemGroupMapper;

    public OrderMapper() {
        this.itemGroupMapper = new ItemGroupMapper();
    }

    public Order mapDTOToOrder(String customerID, CreateOrderDTO createOrderDTO) {
        return new Order(customerID, itemGroupMapper.mapDTOToItemGroup(createOrderDTO.getOrderList()));
    }

    public OrderDTO mapOrderToDTO(Order order) {
        return new OrderDTO()
                .setOrderID(order.getOrderID())
                .setCustomerID(order.getCustomerID())
                .setOrderDate(order.getOrderDate())
                .setOrderList(itemGroupMapper.mapItemGroupToDTO(order.getOrderList()))
                .setTotalPrice(order.getTotalPrice().toString());

    }
}
