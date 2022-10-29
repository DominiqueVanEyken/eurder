package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;

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
                .setAmount(itemGroup.getAmount())
                .setShippingDate(itemGroup.getShippingDate())
                .setPricePerUnit(itemGroup.getPricePerUnit())
                .setTotalPrice(itemGroup.getTotalPrice().toString());
    }

    public List<ItemGroupDTO> mapItemGroupToDTO(List<ItemGroup> itemGroups) {
        System.out.println("ItemGroup: ItemGroup -> DTO");
        return itemGroups.stream()
                .map(this::mapItemGroupToDTO)
                .toList();
    }
}
