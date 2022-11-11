package com.switchfully.eurder.domain.order;

import com.switchfully.eurder.domain.address.Address;
import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.item.ItemRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemGroupShippingTest {

    ItemRepository itemRepository = new ItemRepository();

    @Test
    void creatingItemGroupShipping() {
        Item item = itemRepository.getAllItemsFromRepository().stream().toList().get(0);
        Address address = new Address("streetName", "1", "1111", "city");
        ItemGroup itemGroup = new ItemGroup(item.getItemID(), item.getName(), 2, item.getShippingDateForAmount(2), item.getPrice());
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