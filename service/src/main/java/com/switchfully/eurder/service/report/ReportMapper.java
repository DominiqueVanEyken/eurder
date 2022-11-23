package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupShippingReport;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
import com.switchfully.eurder.service.report.dto.*;

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

//    public ReportDTO mapOrdersToReportDTO(List<OrderReportDTO> orders) {
//        Price totalPrice = new Price(orders.stream()
//                .mapToDouble(order -> order.getTotalPrice().getPrice())
//                .sum());
//        return new ReportDTO()
//                .setOrderReports()
//                .setTotalPrice(totalPrice.toString());
//    }

    public ShippingReportDTO mapShippingReportToShippingReportDTO(List<ItemGroupShippingReport> itemGroupShippingReports) {
        return new ShippingReportDTO()
                .setShippingDate(LocalDate.now())
                .setItemGroups(mapItemGroupToItemGroupShippingReportDTO(itemGroupShippingReports));
    }

    public ItemGroupShippingReportDTO mapItemGroupToItemGroupShippingReportDTO(ItemGroupShippingReport itemGroupShippingReport) {
        return new ItemGroupShippingReportDTO()
                .setItemID(itemGroupShippingReport.getItemID())
                .setItemName(itemGroupShippingReport.getItemName())
                .setAmount(itemGroupShippingReport.getAmount())
                .setPricePerUnit(itemGroupShippingReport.getPricePerUnit())
                .setTotalPrice(itemGroupShippingReport.getTotalPrice())
                .setShippingAddress(itemGroupShippingReport.getShippingAddress());
    }

    public List<ItemGroupShippingReportDTO> mapItemGroupToItemGroupShippingReportDTO(List<ItemGroupShippingReport> itemGroupShippingReports) {
        return itemGroupShippingReports.stream()
                .map(this::mapItemGroupToItemGroupShippingReportDTO)
                .toList();
    }
}
