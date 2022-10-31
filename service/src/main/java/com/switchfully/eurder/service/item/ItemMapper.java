package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemBuilder;
import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;

import java.util.Collection;
import java.util.List;

public class ItemMapper {
    protected Item mapDTOToItem(CreateItemDTO createItemDTO) {
        return new ItemBuilder()
                .setName(createItemDTO.getName())
                .setDescription(createItemDTO.getDescription())
                .setPrice(new Price(createItemDTO.getPrice()))
                .setStockCount(createItemDTO.getStockCount())
                .build();
    }

    public ItemDTO mapItemToDTO(Item item) {
        return new ItemDTO()
                .setItemID(item.getItemID())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPriceWithUnit())
                .setStockStatus(item.getStockStatus());
    }

    public List<ItemDTO> mapItemToDTO(Collection<Item> items) {
        return items.stream()
                .map(this::mapItemToDTO)
                .toList();
    }
}
