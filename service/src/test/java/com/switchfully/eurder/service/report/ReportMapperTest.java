package com.switchfully.eurder.service.report;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.itemgroup.ItemGroupMapper;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import com.switchfully.eurder.service.report.dto.ItemGroupShippingReportDTO;
import com.switchfully.eurder.service.report.dto.OrderReportDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportMapperTest {
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    private final ReportMapper reportMapper = new ReportMapper();
    @Autowired
    private ItemRepository itemRepository;
    private final String customerID = "CID20221001";
    private final Item item = new Item("name", null, new Price(1.1), 100);
    private final long itemID = item.getItemID();
    private final int amount = 1;
    private final Order order = new Order(customerID);
    private final Address address = new Address("street", "1", new PostalCode("1111", "city"));
    private final ItemGroup itemGroup = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());

    @Test
    void mappingItemGroupToItemGroupReportDTO() {
        ItemGroup itemGroup = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        ItemGroupReportDTO reportDTO = reportMapper.mapItemGroupToItemGroupReportDTO(itemGroup);

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getName()).isEqualTo(itemGroup.getItemName());
        assertThat(reportDTO.getAmount()).isEqualTo(itemGroup.getAmount());
        assertThat(reportDTO.getTotalPrice()).isEqualTo(itemGroup.getTotalPrice().toString());
    }

    @Test
    void mappingItemGroupToItemGroupReportDTO_givenList() {
        ItemGroup itemGroup1 = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        ItemGroup itemGroup2 = new ItemGroup(order, item, item.getName(), amount, item.getShippingDateForAmount(amount), item.getPrice());
        List<ItemGroupReportDTO> orderList = reportMapper.mapItemGroupToItemGroupReportDTO(List.of(itemGroup1, itemGroup2));

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(2);
    }

    @Test
    void mapItemGroupToItemGroupShippingDTO() {
        ItemGroupShippingReportDTO result = reportMapper.mapItemGroupToItemGroupShippingReportDTO(address, itemGroup);

        assertThat(result.getItemID()).isEqualTo(itemGroup.getItemID());
        assertThat(result.getItemName()).isEqualTo(itemGroup.getItemName());
        assertThat(result.getAmount()).isEqualTo(itemGroup.getAmount());
        assertThat(result.getTotalPrice()).isEqualTo(itemGroup.getTotalPrice().toString());
        assertThat(result.getPricePerUnit()).isEqualTo(itemGroup.getPricePerUnit());
        assertThat(result.getShippingAddress()).isEqualTo(address);
    }

    @Test
    void mappingOrdersToOrderReportDTO() {
        OrderReportDTO reportDTO = reportMapper.mapOrderToOrderReportDTO(order, reportMapper.mapItemGroupToItemGroupReportDTO(List.of(itemGroup)));

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getOrderID()).isEqualTo(order.getOrderID());
        assertThat(reportDTO.getItemGroupReports()).isNotNull();
        assertThat(reportDTO.getTotalOrderPrice()).isEqualTo(order.getTotalPrice().toString());
    }
}