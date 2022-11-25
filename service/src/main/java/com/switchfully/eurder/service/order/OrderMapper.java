package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;

import java.util.List;

public class OrderMapper {

    public Order mapDTOToOrder(String customerID) {
        return new Order(customerID);
    }

    public OrderDTO mapOrderToDTO(Order order, List<ItemGroupDTO> itemGroupDTOS) {
        return new OrderDTO()
                .setOrderID(order.getOrderID())
                .setCustomerID(order.getCustomerID())
                .setOrderDate(order.getOrderDate())
                .setOrderList(itemGroupDTOS)
                .setTotalPrice(order.getTotalPrice().toString());
    }
}
