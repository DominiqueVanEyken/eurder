package com.switchfully.eurder.service.order.dto;

import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupShippingDTO;

import java.time.LocalDate;
import java.util.List;

public class ShippingReportDTO {
    private LocalDate shippingDate;
    private List<ItemGroupShippingDTO> itemGroups;

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public List<ItemGroupShippingDTO> getItemGroups() {
        return itemGroups;
    }

    public ShippingReportDTO setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public ShippingReportDTO setItemGroups(List<ItemGroupShippingDTO> itemGroups) {
        this.itemGroups = itemGroups;
        return this;
    }
}
