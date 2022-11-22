package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.address.PostalCode;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ItemGroupShippingReportTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void creatingItemGroupShipping() {
        Item item = itemRepository.findAll().stream().toList().get(0);
        Address address = new Address("streetName", "1", new PostalCode("1111", "city"));
        ItemGroup itemGroup = new ItemGroup(item.getItemID(), item.getName(), 2, item.getShippingDateForAmount(2), item.getPrice());
        ItemGroupShippingReport itemGroupShippingReport = new ItemGroupShippingReport(address.getFullAddressAsString(), itemGroup);

        assertThat(itemGroupShippingReport).isNotNull();
        assertThat(itemGroupShippingReport.getItemID()).isEqualTo(itemGroup.getItemID());
        assertThat(itemGroupShippingReport.getItemName()).isEqualTo(itemGroup.getItemName());
        assertThat(itemGroupShippingReport.getAmount()).isEqualTo(itemGroup.getAmount());
        assertThat(itemGroupShippingReport.getPricePerUnit()).isEqualTo(itemGroup.getPricePerUnit());
        assertThat(itemGroupShippingReport.getTotalPrice()).isEqualTo(itemGroup.getTotalPrice().toString());
        assertThat(itemGroupShippingReport.getShippingAddress()).isEqualTo(address.getFullAddressAsString());
    }

}