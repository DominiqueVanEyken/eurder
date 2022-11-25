package com.switchfully.eurder.service.order;


import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.itemgroup.ItemGroupMapper;
import com.switchfully.eurder.service.itemgroup.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemGroupMapperTest {
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    private final Item item = new Item("name", null, new Price(1.1), 100);
    private final long itemID = item.getItemID();
    private final String itemName = "itemName";
    private final int amount = 1;
    private final LocalDate shippingDate = LocalDate.now().plusDays(1);
    private final Price pricePerUnit = new Price(1.1);
    private final Price totalPrice = new Price(pricePerUnit.getPrice() * amount);
    private final Order order = new Order(UUID.randomUUID().toString());


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
                .setPricePerUnit(pricePerUnit)
                .setTotalPrice(totalPrice);

        assertThat(itemGroupDTO).isNotNull();
        assertThat(itemGroupDTO.getItemID()).isEqualTo(itemID);
        assertThat(itemGroupDTO.getItemName()).isEqualTo(itemName);
        assertThat(itemGroupDTO.getAmount()).isEqualTo(amount);
        assertThat(itemGroupDTO.getShippingDate()).isEqualTo(shippingDate);
        assertThat(itemGroupDTO.getPricePerUnit()).isEqualTo(pricePerUnit);
        assertThat(itemGroupDTO.getTotalPrice()).isEqualTo(totalPrice);
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
        ItemGroup itemGroup = itemGroupMapper.mapItemToItemGroup(order, item, amount);
        assertThat(itemGroup).isNotNull();
        assertThat(itemGroup.getItemID()).isEqualTo(itemID);
        assertThat(itemGroup.getAmount()).isEqualTo(amount);
    }

    @Test
    void mappingItemGroupToDTO() {
        ItemGroup itemGroup = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        ItemGroupDTO itemGroupDTO = itemGroupMapper.mapItemGroupToDTO(itemGroup);

        assertThat(itemGroupDTO).isNotNull();
        assertThat(itemGroupDTO.getItemID()).isEqualTo(itemID);
        assertThat(itemGroupDTO.getAmount()).isEqualTo(amount);
        assertThat(itemGroupDTO.getShippingDate()).isEqualTo(shippingDate);
        assertThat(itemGroupDTO.getPricePerUnit()).isEqualTo(pricePerUnit);
        assertThat(itemGroupDTO.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    void mappingItemGroupToDTO_givenAList() {
        ItemGroup itemGroup1 = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        ItemGroup itemGroup2 = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        List<ItemGroupDTO> orderList = itemGroupMapper.mapItemGroupToDTO(List.of(itemGroup1, itemGroup2));

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(2);
    }


}