package com.switchfully.eurder.service.order.dto;

import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupReportDTO;

import java.util.List;

public class OrderReportDTO {
    private String orderID;
    private List<ItemGroupReportDTO> itemGroupReports;
    private String totalOrderPrice;

    public String getOrderID() {
        return orderID;
    }

    public OrderReportDTO setOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public List<ItemGroupReportDTO> getItemGroupReports() {
        return itemGroupReports;
    }

    public OrderReportDTO setItemGroupReports(List<ItemGroupReportDTO> itemGroupReports) {
        this.itemGroupReports = itemGroupReports;
        return this;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public OrderReportDTO setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
        return this;
    }
}
