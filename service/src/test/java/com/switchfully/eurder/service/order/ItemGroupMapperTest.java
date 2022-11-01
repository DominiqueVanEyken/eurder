package com.switchfully.eurder.service.order;


import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.ItemGroupShipping;
import com.switchfully.eurder.service.order.dto.itemgroup.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupReportDTO;
import com.switchfully.eurder.service.order.dto.itemgroup.ItemGroupShippingDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemGroupMapperTest {
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    private final Item item = new Item("name", null, new Price(1.1), 100);
    private final String itemID = item.getItemID();
    private final String itemName = "itemName";
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
                .setItemName(itemName)
                .setAmount(amount)
                .setShippingDate(shippingDate)
                .setPricePerUnit(pricePerUnit.toString())
                .setTotalPrice(totalPrice.toString());

        assertThat(itemGroupDTO).isNotNull();
        assertThat(itemGroupDTO.getItemID()).isEqualTo(itemID);
        assertThat(itemGroupDTO.getItemName()).isEqualTo(itemName);
        assertThat(itemGroupDTO.getAmount()).isEqualTo(amount);
        assertThat(itemGroupDTO.getShippingDate()).isEqualTo(shippingDate);
        assertThat(itemGroupDTO.getPricePerUnit()).isEqualTo(pricePerUnit.toString());
        assertThat(itemGroupDTO.getTotalPrice()).isEqualTo(totalPrice.toString());
    }

    @Test
    void creatingItemGroupReportDTO(){
        String name = "name";
        int amount = 2;
        Price price = new Price(2.2);
        ItemGroupReportDTO reportDTO = new ItemGroupReportDTO()
                .setName(name)
                .setAmount(amount)
                .setTotalPrice(price.toString());

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getName()).isEqualTo(name);
        assertThat(reportDTO.getAmount()).isEqualTo(amount);
        assertThat(reportDTO.getTotalPrice()).isEqualTo(price.toString());
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

    @Test
    void mappingItemGroupToItemGroupReportDTO() {
        ItemGroup itemGroup = new ItemGroup(itemID, amount);
        itemGroup.setShippingDateAndPrice(item);
        ItemGroupReportDTO reportDTO = itemGroupMapper.mapItemGroupToItemGroupReportDTO(itemGroup);

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getName()).isEqualTo(itemGroup.getItemName());
        assertThat(reportDTO.getAmount()).isEqualTo(itemGroup.getAmount());
        assertThat(reportDTO.getTotalPrice()).isEqualTo(itemGroup.getTotalPrice().toString());
    }

    @Test
    void mappingItemGroupToItemGroupReportDTO_givenList() {
        ItemGroup itemGroup1 = new ItemGroup(itemID, amount);
        itemGroup1.setShippingDateAndPrice(item);
        ItemGroup itemGroup2 = new ItemGroup(itemID, amount);
        itemGroup2.setShippingDateAndPrice(item);
        List<ItemGroupReportDTO> orderList = itemGroupMapper.mapItemGroupToItemGroupReportDTO(List.of(itemGroup1, itemGroup2));

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    void mapItemGroupToItemGroupShippingDTO() {
        String address = "address";
        ItemGroup itemGroup = new ItemGroup(itemID, amount);
        itemGroup.setShippingDateAndPrice(item);
        ItemGroupShipping itemGroupShipping = new ItemGroupShipping(address, itemGroup);

        ItemGroupShippingDTO result = itemGroupMapper.mapItemGroupToItemGroupShippingDTO(itemGroupShipping);

        assertThat(result.getItemID()).isEqualTo(itemGroupShipping.getItemID());
        assertThat(result.getItemName()).isEqualTo(itemGroupShipping.getItemName());
        assertThat(result.getAmount()).isEqualTo(itemGroupShipping.getAmount());
        assertThat(result.getTotalPrice()).isEqualTo(itemGroupShipping.getTotalPrice());
        assertThat(result.getPricePerUnit()).isEqualTo(itemGroupShipping.getPricePerUnit());
        assertThat(result.getShippingAddress()).isEqualTo(itemGroupShipping.getShippingAddress());
    }

    @Test
    void mapItemGroupToItemGroupShippingDTO_givenList() {
        String address = "address";
        ItemGroup itemGroup = new ItemGroup(itemID, amount);
        itemGroup.setShippingDateAndPrice(item);
        List<ItemGroupShipping> itemGroupShipping = List.of(new ItemGroupShipping(address, itemGroup), new ItemGroupShipping(address, itemGroup));

        List<ItemGroupShippingDTO> result = itemGroupMapper.mapItemGroupToItemGroupShippingDTO(itemGroupShipping);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(itemGroupShipping.size());
    }
}