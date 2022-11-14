package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.order.ItemGroup;
import com.switchfully.eurder.domain.order.ItemGroupShipping;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.ItemGroupMapper;
import com.switchfully.eurder.service.report.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportMapperTest {
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    private final ReportMapper reportMapper = new ReportMapper();
    private final ItemRepository itemRepository = new ItemRepository();
    private final String customerID = "CID20221001";
    private final Item item = new Item("name", null, new Price(1.1), 100);
    private final String itemID = item.getItemID();
    private final int amount = 1;

    @Test
    void mappingItemGroupToItemGroupReportDTO() {
        ItemGroup itemGroup = new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        ItemGroupReportDTO reportDTO = reportMapper.mapItemGroupToItemGroupReportDTO(itemGroup);

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getName()).isEqualTo(itemGroup.getItemName());
        assertThat(reportDTO.getAmount()).isEqualTo(itemGroup.getAmount());
        assertThat(reportDTO.getTotalPrice()).isEqualTo(itemGroup.getTotalPrice().toString());
    }

    @Test
    void mappingItemGroupToItemGroupReportDTO_givenList() {
        ItemGroup itemGroup1 = new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        ItemGroup itemGroup2 = new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        List<ItemGroupReportDTO> orderList = reportMapper.mapItemGroupToItemGroupReportDTO(List.of(itemGroup1, itemGroup2));

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    void mapItemGroupToItemGroupShippingDTO() {
        String address = "address";
        ItemGroup itemGroup = new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
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
        ItemGroup itemGroup = new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        List<ItemGroupShipping> itemGroupShipping = List.of(new ItemGroupShipping(address, itemGroup), new ItemGroupShipping(address, itemGroup));

        List<ItemGroupShippingDTO> result = itemGroupMapper.mapItemGroupToItemGroupShippingDTO(itemGroupShipping);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(itemGroupShipping.size());
    }

    @Test
    void mappingOrdersToOrderReportDTO() {
        itemRepository.addItem(item);
        Order order = new Order(customerID, List.of(new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice())));
        OrderReportDTO reportDTO = reportMapper.mapOrderToOrderReportDTO(order);

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getOrderID()).isEqualTo(order.getOrderID());
        assertThat(reportDTO.getItemGroupReports()).isNotNull();
        assertThat(reportDTO.getTotalOrderPrice()).isEqualTo(order.getTotalPrice().toString());
    }

    @Test
    void mappingOrdersToReportDTO() {
        itemRepository.addItem(item);
        Order order1 = new Order(customerID, List.of(new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice())));

        ReportDTO reportDTO = reportMapper.mapOrdersToReportDTO(List.of(order1, order1));

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getTotalPrice()).isEqualTo(new Price(order1.getTotalPrice().getPrice() * 2).toString());
        assertThat(reportDTO.getOrderReports()).isNotNull();
    }

    @Test
    void mapShippingReportToShippingReportDTO() {
        String address = "address";
        Item testItem = itemRepository.getAllItemsFromRepository().stream().toList().get(0);
        ItemGroup itemGroup = new ItemGroup(testItem.getItemID(), testItem.getName(), amount, testItem.getShippingDateForAmount(amount), testItem.getPrice());
        List<ItemGroupShipping> itemGroupShipping = List.of(new ItemGroupShipping(address, itemGroup), new ItemGroupShipping(address, itemGroup));

        ShippingReportDTO result = reportMapper.mapShippingReportToShippingReportDTO(itemGroupShipping);

        assertThat(result).isNotNull();
        assertThat(result.getShippingDate()).isEqualTo(LocalDate.now());
        assertThat(result.getItemGroups().size()).isEqualTo(itemGroupShipping.size());
    }
}