package com.switchfully.eurder.service.report.dto;

import java.time.LocalDate;
import java.util.List;

public class ShippingReportDTO {
    private LocalDate shippingDate;
    private List<ItemGroupShippingReportDTO> itemGroups;

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public List<ItemGroupShippingReportDTO> getItemGroups() {
        return itemGroups;
    }

    public ShippingReportDTO setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public ShippingReportDTO setItemGroups(List<ItemGroupShippingReportDTO> itemGroups) {
        this.itemGroups = itemGroups;
        return this;
    }
}
