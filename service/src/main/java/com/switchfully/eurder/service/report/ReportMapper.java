package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.ItemGroupShipping;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.ItemGroupMapper;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import com.switchfully.eurder.service.report.dto.OrderReportDTO;
import com.switchfully.eurder.service.report.dto.ReportDTO;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;

import java.time.LocalDate;
import java.util.List;

public class ReportMapper {
    private final ItemGroupMapper itemGroupMapper;

    public ReportMapper() {
        this.itemGroupMapper = new ItemGroupMapper();
    }

    public OrderReportDTO mapOrderToOrderReportDTO(Order order) {
        return new OrderReportDTO()
                .setOrderID(order.getOrderID())
                .setItemGroupReports(mapItemGroupToItemGroupReportDTO(order.getOrderList()))
                .setTotalOrderPrice(order.getTotalPrice().toString());
    }

    public ItemGroupReportDTO mapItemGroupToItemGroupReportDTO(ItemGroup itemGroup) {
        return new ItemGroupReportDTO()
                .setName(itemGroup.getItemName())
                .setAmount(itemGroup.getAmount())
                .setTotalPrice(itemGroup.getTotalPrice().toString());
    }

    public List<ItemGroupReportDTO> mapItemGroupToItemGroupReportDTO(List<ItemGroup> itemGroups) {
        return itemGroups.stream()
                .map(this::mapItemGroupToItemGroupReportDTO)
                .toList();
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

    public ShippingReportDTO mapShippingReportToShippingReportDTO(List<ItemGroupShipping> itemGroupShippings) {
        return new ShippingReportDTO()
                .setShippingDate(LocalDate.now())
                .setItemGroups(itemGroupMapper.mapItemGroupToItemGroupShippingDTO(itemGroupShippings));
    }
}
