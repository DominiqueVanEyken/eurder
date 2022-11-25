package com.switchfully.eurder.service.report.dto;

import java.util.List;

public class OrderReportDTO {
    private long orderID;
    private List<ItemGroupReportDTO> itemGroupReports;
    private String totalOrderPrice;

    public long getOrderID() {
        return orderID;
    }

    public OrderReportDTO setOrderID(long orderID) {
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
