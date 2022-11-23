package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;

import java.util.List;

public class OrderMapper {

    public Order mapDTOToOrder(String customerID) {
        return new Order(customerID);
//        return new Order(customerID, itemGroups);
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
