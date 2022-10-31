package com.switchfully.eurder.service.item;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {
    private final ItemMapper itemMapper = new ItemMapper();
    private final String itemID = "IID20221001";
    private final String name = "name";
    private final String description = "description";
    private final double price = 1.1;
    private final int stockCount = 2;

    @Test
    void creatingCreateItemDTO() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setStockCount(stockCount);

        assertThat(createItemDTO).isNotNull();
        assertThat(createItemDTO.getName()).isEqualTo(name);
        assertThat(createItemDTO.getDescription()).isEqualTo(description);
        assertThat(createItemDTO.getPrice()).isEqualTo(price);
        assertThat(createItemDTO.getStockCount()).isEqualTo(stockCount);

    }

    @Test
    void creatingItemDTO() {
        ItemDTO itemDTO = new ItemDTO()
                .setItemID(itemID)
                .setName(name)
                .setDescription(description)
                .setPrice(new Price(price).toString())
                .setStockCount(stockCount);

        assertThat(itemDTO).isNotNull();
        assertThat(itemDTO.getItemID()).isEqualTo(itemID);
        assertThat(itemDTO.getName()).isEqualTo(name);
        assertThat(itemDTO.getDescription()).isEqualTo(description);
        assertThat(itemDTO.getPrice()).isEqualTo(new Price(price).toString());
        assertThat(itemDTO.getStockCount()).isEqualTo(stockCount);
    }

    @Test
    void mappingItemToDTO() {
        Item item = new Item(name, description, new Price(price), stockCount);

        ItemDTO itemDTO = itemMapper.mapItemToDTO(item);

        assertThat(itemDTO).isNotNull();
        assertThat(itemDTO.getName()).isEqualTo(name);
        assertThat(itemDTO.getDescription()).isEqualTo(description);
        assertThat(itemDTO.getPrice()).isEqualTo(new Price(price).toString());
        assertThat(itemDTO.getStockCount()).isEqualTo(stockCount);
    }

    @Test
    void mappingItemToDTO_givenList() {
        Item item1 = new Item(name, description, new Price(price), stockCount);
        Item item2 = new Item(name, description, new Price(price), stockCount);
        List<Item> itemList = List.of(item1, item2);

        List<ItemDTO> itemDTO = itemMapper.mapItemToDTO(itemList);

        assertThat(itemDTO).isNotNull();
        assertThat(itemDTO.size()).isGreaterThan(0);
    }

    @Test
    void mappingDTOToItem() {
        CreateItemDTO createItemDTO = new CreateItemDTO()
                .setName(name)
                .setDescription(description)
                .setPrice(price)
                .setStockCount(stockCount);

        Item item = itemMapper.mapDTOToItem(createItemDTO);

        assertThat(item).isNotNull();
        assertThat(item.getName()).isEqualTo(name);
        assertThat(item.getDescription()).isEqualTo(description);
        assertThat(item.getPriceWithUnit()).isEqualTo(new Price(price).toString());
        assertThat(item.getStockCount()).isEqualTo(stockCount);
    }
}