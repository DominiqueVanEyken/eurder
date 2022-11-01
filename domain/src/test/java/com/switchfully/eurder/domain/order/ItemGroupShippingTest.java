package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemGroupShippingTest {

    ItemRepository itemRepository = new ItemRepository();

    @Test
    void creatingItemGroupShipping() {
        String itemID = "IID20221001";
        Address address = new Address("streetName", "1", "1111", "city");
        ItemGroup itemGroup = new ItemGroup(itemID, 2);
        itemGroup.setShippingDateAndPrice(itemRepository.getItemByID(itemID));
        ItemGroupShipping itemGroupShipping = new ItemGroupShipping(address.getFullAddressAsString(), itemGroup);

        assertThat(itemGroupShipping).isNotNull();
        assertThat(itemGroupShipping.getItemID()).isEqualTo(itemGroup.getItemID());
        assertThat(itemGroupShipping.getItemName()).isEqualTo(itemGroup.getItemName());
        assertThat(itemGroupShipping.getAmount()).isEqualTo(itemGroup.getAmount());
        assertThat(itemGroupShipping.getPricePerUnit()).isEqualTo(itemGroup.getPricePerUnit());
        assertThat(itemGroupShipping.getTotalPrice()).isEqualTo(itemGroup.getTotalPrice().toString());
        assertThat(itemGroupShipping.getShippingAddress()).isEqualTo(address.getFullAddressAsString());
    }

}