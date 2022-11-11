package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.dto.OrderDTO;

import java.util.List;

public class OrderMapper {
    private final ItemGroupMapper itemGroupMapper;

    public OrderMapper() {
        this.itemGroupMapper = new ItemGroupMapper();
    }

    public Order mapDTOToOrder(String customerID, List<ItemGroup> itemGroups) {
        return new Order(customerID, itemGroups);
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
