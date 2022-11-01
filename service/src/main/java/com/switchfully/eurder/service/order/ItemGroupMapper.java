package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.ShippingReport;
import com.switchfully.eurder.service.order.dto.itemgroup.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupReportDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupShippingDTO;

import java.util.List;

public class ItemGroupMapper {

    public List<ItemGroup> mapDTOToItemGroup(List<CreateItemGroupDTO> createItemGroupDTO) {
        return createItemGroupDTO.stream()
                .map(this::mapDTOToItemGroup)
                .toList();
    }

    public ItemGroup mapDTOToItemGroup(CreateItemGroupDTO createItemGroupDTO) {
        return new ItemGroup(createItemGroupDTO.getItemID(), createItemGroupDTO.getAmount());
    }

    public ItemGroupDTO mapItemGroupToDTO(ItemGroup itemGroup) {
        return new ItemGroupDTO()
                .setItemID(itemGroup.getItemID())
                .setItemName(itemGroup.getItemName())
                .setAmount(itemGroup.getAmount())
                .setShippingDate(itemGroup.getShippingDate())
                .setPricePerUnit(itemGroup.getPricePerUnit())
                .setTotalPrice(itemGroup.getTotalPrice().toString());
    }

    public List<ItemGroupDTO> mapItemGroupToDTO(List<ItemGroup> itemGroups) {
        return itemGroups.stream()
                .map(this::mapItemGroupToDTO)
                .toList();
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

    public ItemGroupShippingDTO mapItemGroupToItemGroupShippingDTO(ShippingReport shippingReport) {
        return new ItemGroupShippingDTO()
                .setItemID(shippingReport.getItemID())
                .setItemName(shippingReport.getItemName())
                .setAmount(shippingReport.getAmount())
                .setPricePerUnit(shippingReport.getPricePerUnit())
                .setTotalPrice(shippingReport.getTotalPrice())
                .setShippingAddress(shippingReport.getShippingAddress());
    }

    public List<ItemGroupShippingDTO> mapItemGroupToItemGroupShippingDTO(List<ShippingReport> shippingReportPerItemGroup) {
        return shippingReportPerItemGroup.stream()
                .map(this::mapItemGroupToItemGroupShippingDTO)
                .toList();
    }
}
