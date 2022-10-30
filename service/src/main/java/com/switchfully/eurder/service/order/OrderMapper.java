package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.order.dto.OrderReportDTO;
import com.switchfully.eurder.service.order.dto.report.ReportDTO;

import java.util.List;

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

    public OrderReportDTO mapOrderToOrderReportDTO(Order order) {
        return new OrderReportDTO()
                .setOrderID(order.getOrderID())
                .setItemGroupReports(itemGroupMapper.mapItemGroupToItemGroupReportDTO(order.getOrderList()))
                .setTotalOrderPrice(order.getTotalPrice().toString());
    }



    public ReportDTO mapOrdersToReportDTO(List<Order> orders) {
        Price totalPrice = new Price(orders.stream()
                .mapToDouble(order -> order.getTotalPrice().getPrice())
                .sum());
        return new ReportDTO()
                .setOrderReports(orders.stream()
                        .map(this::mapOrderToOrderReportDTO)
                        .toList())
                .setTotalPrice(totalPrice.toString());

    }
}
