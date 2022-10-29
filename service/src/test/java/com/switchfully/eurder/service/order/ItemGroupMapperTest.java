package com.switchfully.eurder.service.order;


import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.service.order.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.ItemGroupDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemGroupMapperTest {
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    private final Item item = new Item("name", null, new Price(1.1), 100);
    private final String itemID = item.getItemID();
    private final int amount = 1;
    private final LocalDate shippingDate = LocalDate.now().plusDays(1);
    private final Price pricePerUnit = new Price(1.1);
    private final Price totalPrice = new Price(pricePerUnit.getPrice() * amount);


    @Test
    void creatingCreateItemGroupDTO() {
        CreateItemGroupDTO createItemGroupDTO = new CreateItemGroupDTO()
                .setItemID(itemID)
                .setAmount(amount);
        assertThat(createItemGroupDTO).isNotNull();
        assertThat(createItemGroupDTO.getItemID()).isEqualTo(itemID);
        assertThat(createItemGroupDTO.getAmount()).isEqualTo(amount);
    }

    @Test
    void creatingItemGroupDTO() {
        ItemGroupDTO itemGroupDTO = new ItemGroupDTO()
                .setItemID(itemID)
                .setAmount(amount)
                .setShippingDate(shippingDate)
                .setPricePerUnit(pricePerUnit.toString())
                .setTotalPrice(totalPrice.toString());

        assertThat(itemGroupDTO).isNotNull();
        assertThat(itemGroupDTO.getItemID()).isEqualTo(itemID);
        assertThat(itemGroupDTO.getAmount()).isEqualTo(amount);
        assertThat(itemGroupDTO.getShippingDate()).isEqualTo(shippingDate);
        assertThat(itemGroupDTO.getPricePerUnit()).isEqualTo(pricePerUnit.toString());
        assertThat(itemGroupDTO.getTotalPrice()).isEqualTo(totalPrice.toString());
    }

    @Test
    void mappingDTOToItemGroup() {
        CreateItemGroupDTO createItemGroupDTO = new CreateItemGroupDTO()
                .setItemID(itemID)
                .setAmount(amount);
        ItemGroup itemGroup = itemGroupMapper.mapDTOToItemGroup(createItemGroupDTO);
        assertThat(itemGroup).isNotNull();
        assertThat(itemGroup.getItemID()).isEqualTo(itemID);
        assertThat(itemGroup.getAmount()).isEqualTo(amount);
    }

    @Test
    void mappingDTOToItemGroup_givenAList() {
        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO()
                .setItemID(itemID)
                .setAmount(amount);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO()
                .setItemID(itemID)
                .setAmount(amount);
        List<ItemGroup> orderList = itemGroupMapper.mapDTOToItemGroup(List.of(createItemGroupDTO1, createItemGroupDTO2));

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    void mappingItemGroupToDTO() {
        ItemGroup itemGroup = new ItemGroup(itemID, amount);
        itemGroup.setShippingDateAndPrice(item);
        ItemGroupDTO itemGroupDTO = itemGroupMapper.mapItemGroupToDTO(itemGroup);

        assertThat(itemGroupDTO).isNotNull();
        assertThat(itemGroupDTO.getItemID()).isEqualTo(itemID);
        assertThat(itemGroupDTO.getAmount()).isEqualTo(amount);
        assertThat(itemGroupDTO.getShippingDate()).isEqualTo(shippingDate);
        assertThat(itemGroupDTO.getPricePerUnit()).isEqualTo(pricePerUnit.toString());
        assertThat(itemGroupDTO.getTotalPrice()).isEqualTo(totalPrice.toString());
    }

    @Test
    void mappingItemGroupToDTO_givenAList() {
        ItemGroup itemGroup1 = new ItemGroup(itemID, amount);
        itemGroup1.setShippingDateAndPrice(item);
        ItemGroup itemGroup2 = new ItemGroup(itemID, amount);
        itemGroup2.setShippingDateAndPrice(item);
        List<ItemGroupDTO> orderList = itemGroupMapper.mapItemGroupToDTO(List.of(itemGroup1, itemGroup2));

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(2);
    }
}