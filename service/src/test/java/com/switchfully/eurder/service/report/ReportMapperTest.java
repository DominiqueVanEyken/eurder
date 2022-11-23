package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupShippingReport;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.order.ItemGroupMapper;
import com.switchfully.eurder.service.report.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportMapperTest {
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    private final ReportMapper reportMapper = new ReportMapper();
    @Autowired
    private ItemRepository itemRepository;
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
        ItemGroupShippingReport itemGroupShippingReport = new ItemGroupShippingReport(address, itemGroup);

        ItemGroupShippingReportDTO result = reportMapper.mapItemGroupToItemGroupShippingReportDTO(itemGroupShippingReport);

        assertThat(result.getItemID()).isEqualTo(itemGroupShippingReport.getItemID());
        assertThat(result.getItemName()).isEqualTo(itemGroupShippingReport.getItemName());
        assertThat(result.getAmount()).isEqualTo(itemGroupShippingReport.getAmount());
        assertThat(result.getTotalPrice()).isEqualTo(itemGroupShippingReport.getTotalPrice());
        assertThat(result.getPricePerUnit()).isEqualTo(itemGroupShippingReport.getPricePerUnit());
        assertThat(result.getShippingAddress()).isEqualTo(itemGroupShippingReport.getShippingAddress());
    }

    @Test
    void mapItemGroupToItemGroupShippingDTO_givenList() {
        String address = "address";
        ItemGroup itemGroup = new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        List<ItemGroupShippingReport> itemGroupShippingReport = List.of(new ItemGroupShippingReport(address, itemGroup), new ItemGroupShippingReport(address, itemGroup));

        List<ItemGroupShippingReportDTO> result = reportMapper.mapItemGroupToItemGroupShippingReportDTO(itemGroupShippingReport);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(itemGroupShippingReport.size());
    }

    @Test
    void mappingOrdersToOrderReportDTO() {
        itemRepository.save(item);
        Order order = new Order(customerID, List.of(new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice())));
        OrderReportDTO reportDTO = reportMapper.mapOrderToOrderReportDTO(order);

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getOrderID()).isEqualTo(order.getOrderID());
        assertThat(reportDTO.getItemGroupReports()).isNotNull();
        assertThat(reportDTO.getTotalOrderPrice()).isEqualTo(order.getTotalPrice().toString());
    }

    @Test
    void mappingOrdersToReportDTO() {
        itemRepository.save(item);
        Order order1 = new Order(customerID, List.of(new ItemGroup(item.getItemID(), item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice())));

        ReportDTO reportDTO = reportMapper.mapOrdersToReportDTO(List.of(order1, order1));

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getTotalPrice()).isEqualTo(new Price(order1.getTotalPrice().getPrice() * 2).toString());
        assertThat(reportDTO.getOrderReports()).isNotNull();
    }

    @Test
    void mapShippingReportToShippingReportDTO() {
        String address = "address";
        Item testItem = itemRepository.findAll().stream().toList().get(0);
        ItemGroup itemGroup = new ItemGroup(testItem.getItemID(), testItem.getName(), amount, testItem.getShippingDateForAmount(amount), testItem.getPrice());
        List<ItemGroupShippingReport> itemGroupShippingReport = List.of(new ItemGroupShippingReport(address, itemGroup), new ItemGroupShippingReport(address, itemGroup));

        ShippingReportDTO result = reportMapper.mapShippingReportToShippingReportDTO(itemGroupShippingReport);

        assertThat(result).isNotNull();
        assertThat(result.getShippingDate()).isEqualTo(LocalDate.now());
        assertThat(result.getItemGroups().size()).isEqualTo(itemGroupShippingReport.size());
    }
}