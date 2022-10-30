package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.ItemGroupReportDTO;

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
        return itemGroups.stream()
                .map(this::mapItemGroupToDTO)
                .toList();
    }

    public ItemGroupReportDTO mapItemGroupToItemGroupReportDTO(ItemGroup itemGroup) {
        return new ItemGroupReportDTO()
                .setName("name")
                .setAmount(itemGroup.getAmount())
                .setTotalPrice(itemGroup.getTotalPrice().toString());
    }

    public List<ItemGroupReportDTO> mapItemGroupToItemGroupReportDTO(List<ItemGroup> itemGroups) {
        return itemGroups.stream()
                .map(this::mapItemGroupToItemGroupReportDTO)
                .toList();
    }
}
