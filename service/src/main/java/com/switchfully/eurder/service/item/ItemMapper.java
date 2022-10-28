package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemBuilder;
import com.switchfully.eurder.domain.item.Price;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;

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
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPriceWithUnit())
                .setStockCount(item.getStockCount());
    }
}
