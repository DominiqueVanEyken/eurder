package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.ItemGroupBuilder;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;

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

}
