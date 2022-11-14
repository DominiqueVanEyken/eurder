package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.ItemGroupBuilder;
import com.switchfully.eurder.domain.order.ItemGroupShipping;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import com.switchfully.eurder.service.report.dto.ItemGroupShippingDTO;

import java.util.List;

public class ItemGroupMapper {
    public ItemGroup mapItemToItemGroup(Item item, int amount) {
        return new ItemGroupBuilder()
                .setItemID(item.getItemID())
                .setItemName(item.getName())
                .setAmount(amount)
                .setShippingDate(item.getShippingDateForAmount(amount))
                .setPricePerUnit(item.getPrice())
                .build();
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

    public ItemGroupShippingDTO mapItemGroupToItemGroupShippingDTO(ItemGroupShipping itemGroupShipping) {
        return new ItemGroupShippingDTO()
                .setItemID(itemGroupShipping.getItemID())
                .setItemName(itemGroupShipping.getItemName())
                .setAmount(itemGroupShipping.getAmount())
                .setPricePerUnit(itemGroupShipping.getPricePerUnit())
                .setTotalPrice(itemGroupShipping.getTotalPrice())
                .setShippingAddress(itemGroupShipping.getShippingAddress());
    }

    public List<ItemGroupShippingDTO> mapItemGroupToItemGroupShippingDTO(List<ItemGroupShipping> itemGroupShippings) {
        return itemGroupShippings.stream()
                .map(this::mapItemGroupToItemGroupShippingDTO)
                .toList();
    }
}
