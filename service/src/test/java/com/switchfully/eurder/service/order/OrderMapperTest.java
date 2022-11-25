package com.switchfully.eurder.service.order;

import com.switchfully.eurder.domain.Price.Price;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.itemgroup.ItemGroupMapper;
import com.switchfully.eurder.service.itemgroup.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.order.dto.CreateOrderDTO;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;
import com.switchfully.eurder.service.order.dto.OrderDTO;
import com.switchfully.eurder.service.report.dto.ItemGroupReportDTO;
import com.switchfully.eurder.service.report.dto.OrderReportDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {
    private final String customerID = UUID.randomUUID().toString();
    private final long orderID = 101L;
    private final Item item = new Item("name", null, new Price(1.1), 100);
    private final int amount = 2;
    private final LocalDate orderDate = LocalDate.now();
    private final List<CreateItemGroupDTO> createItemGroupDTOS = List.of(
            new CreateItemGroupDTO()
                    .setItemID(item.getItemID())
                    .setAmount(amount)
    );
    private final List<ItemGroupDTO> itemGroupDTOS = List.of(
            new ItemGroupDTO()
                    .setItemID(item.getItemID())
                    .setAmount(amount)
                    .setShippingDate(LocalDate.now().plusDays(1))
                    .setPricePerUnit(item.getPrice())
                    .setTotalPrice(new Price(item.getPrice().getPrice() * amount))
    );
    private final Order order = new Order(customerID);
    private final Price totalPrice = new Price(2.2);
    private final OrderMapper orderMapper = new OrderMapper();
    private final ItemGroupMapper itemGroupMapper = new ItemGroupMapper();
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void creatingCreateOrderDTO() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setOrderList(createItemGroupDTOS);

        assertThat(createOrderDTO).isNotNull();
        assertThat(createOrderDTO.getOrderList()).isEqualTo(createItemGroupDTOS);
    }

    @Test
    void CreatingOrderDTO() {
        OrderDTO orderDTO = new OrderDTO()
                .setCustomerID(customerID)
                .setOrderDate(orderDate)
                .setOrderList(itemGroupDTOS)
                .setOrderID(1001L)
                .setTotalPrice(totalPrice.toString());

        assertThat(orderDTO).isNotNull();
        assertThat(orderDTO.getOrderID()).isNotNull();
        assertThat(orderDTO.getOrderDate()).isEqualTo(orderDate);
        assertThat(orderDTO.getCustomerID()).isEqualTo(customerID);
        assertThat(orderDTO.getOrderList()).isEqualTo(itemGroupDTOS);
        assertThat(orderDTO.getTotalPrice()).isEqualTo(totalPrice.toString());
    }

    @Test
    void creatingOrderReportDTO() {
        String name = "name";
        int amount = 2;
        Price itemGroupPrice = new Price(2.2);
        Price orderPrice = new Price(4.4);
        ItemGroupReportDTO itemGroupReportDTO1 = new ItemGroupReportDTO()
                .setName(name)
                .setAmount(amount)
                .setTotalPrice(itemGroupPrice.toString());
        ItemGroupReportDTO itemGroupReportDTO2 = new ItemGroupReportDTO()
                .setName(name)
                .setAmount(amount)
                .setTotalPrice(itemGroupPrice.toString());
        List<ItemGroupReportDTO> itemGroupList = List.of(itemGroupReportDTO1, itemGroupReportDTO2);
        OrderReportDTO reportDTO = new OrderReportDTO()
                .setOrderID(orderID)
                .setItemGroupReports(itemGroupList)
                .setTotalOrderPrice(orderPrice.toString());

        assertThat(reportDTO).isNotNull();
        assertThat(reportDTO.getOrderID()).isEqualTo(orderID);
        assertThat(reportDTO.getItemGroupReports()).isEqualTo(itemGroupList);
        assertThat(reportDTO.getTotalOrderPrice()).isEqualTo(orderPrice.toString());
    }

    @Test
    void mappingDTOToOrder() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO()
                .setOrderList(createItemGroupDTOS);
        List<ItemGroup> itemGroups = createOrderDTO.getOrderList().stream()
                .map(itemGroup -> itemGroupMapper.mapItemToItemGroup(order, item, itemGroup.getAmount()))
                .toList();

        Order order = orderMapper.mapDTOToOrder(customerID);
        order.updatePrice(new Price(itemGroups.stream()
                .map(ItemGroup::getTotalPrice)
                .mapToDouble(Price::getPrice)
                .sum()));

        assertThat(order).isNotNull();
        assertThat(order.getOrderID()).isNotNull();
        assertThat(order.getOrderDate()).isEqualTo(orderDate);
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    void mappingOrderToDTO() {
        List<ItemGroup> itemGroups = List.of(new ItemGroup(order, item, amount));
        order.updatePrice(new Price(itemGroups.stream()
                .map(ItemGroup::getTotalPrice)
                .mapToDouble(Price::getPrice)
                .sum()));

        List<ItemGroupDTO> itemGroupDTOS = itemGroupMapper.mapItemGroupToDTO(itemGroups);
        OrderDTO orderDTO = orderMapper.mapOrderToDTO(order, itemGroupDTOS);

        assertThat(orderDTO).isNotNull();
        assertThat(orderDTO.getOrderID()).isNotNull();
        assertThat(orderDTO.getCustomerID()).isEqualTo(customerID);
        assertThat(orderDTO.getOrderDate()).isEqualTo(orderDate);
        assertThat(orderDTO.getTotalPrice()).isEqualTo(totalPrice.toString());
    }

}