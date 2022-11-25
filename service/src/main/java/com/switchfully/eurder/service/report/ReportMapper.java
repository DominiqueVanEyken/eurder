package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import com.switchfully.eurder.service.report.dto.ItemGroupShippingReportDTO;
import com.switchfully.eurder.service.report.dto.OrderReportDTO;
import com.switchfully.eurder.service.report.dto.ShippingReportDTO;

import java.time.LocalDate;
import java.util.List;

public class ReportMapper {

    public OrderReportDTO mapOrderToOrderReportDTO(Order order, List<ItemGroupReportDTO> itemGroupReportDTOS) {
        return new OrderReportDTO()
                .setOrderID(order.getOrderID())
                .setItemGroupReports(itemGroupReportDTOS)
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

    public ShippingReportDTO mapShippingReportToShippingReportDTO(List<ItemGroupShippingReportDTO> itemGroupShippingReportDTOS) {
        return new ShippingReportDTO()
                .setShippingDate(LocalDate.now())
                .setItemGroups(itemGroupShippingReportDTOS);
    }

    public ItemGroupShippingReportDTO mapItemGroupToItemGroupShippingReportDTO(Address shippingAddress, ItemGroup itemGroup) {
        return new ItemGroupShippingReportDTO()
                .setItemID(itemGroup.getItemID())
                .setItemName(itemGroup.getItemName())
                .setAmount(itemGroup.getAmount())
                .setPricePerUnit(itemGroup.getPricePerUnit())
                .setTotalPrice(itemGroup.getTotalPrice())
                .setShippingAddress(shippingAddress);
    }
}
