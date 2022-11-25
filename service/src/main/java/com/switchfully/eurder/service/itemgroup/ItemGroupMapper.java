package com.switchfully.eurder.service.itemgroup;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupBuilder;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;

import java.util.List;

public class ItemGroupMapper {
    public ItemGroup mapItemToItemGroup(Order order, Item item, int amount) {
        return new ItemGroupBuilder()
                .setOrder(order)
                .setItem(item)
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
                .setTotalPrice(itemGroup.getTotalPrice());
    }

    public List<ItemGroupDTO> mapItemGroupToDTO(List<ItemGroup> itemGroups) {
        return itemGroups.stream()
                .map(this::mapItemGroupToDTO)
                .toList();
    }

}
