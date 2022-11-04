package com.switchfully.eurder.service.report.dto;

import java.util.List;

public class ReportDTO {
    private List<OrderReportDTO> orderReports;
    private String totalPrice;

    public List<OrderReportDTO> getOrderReports() {
        return orderReports;
    }

    public ReportDTO setOrderReports(List<OrderReportDTO> orderReports) {
        this.orderReports = orderReports;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public ReportDTO setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
