package com.switchfully.eurder.service.itemgroup;

import com.switchfully.eurder.domain.item.Item;
import com.switchfully.eurder.domain.itemgroup.ItemGroup;
import com.switchfully.eurder.domain.itemgroup.ItemGroupRepository;
import com.switchfully.eurder.domain.order.Order;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.itemgroup.dto.CreateItemGroupDTO;
import com.switchfully.eurder.service.itemgroup.dto.ItemGroupDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemGroupService {
    private final ItemGroupRepository itemGroupRepository;
    private final ItemGroupMapper itemGroupMapper;
    private final ItemService itemService;

    public ItemGroupService(ItemGroupRepository itemGroupRepository, ItemService itemService) {
        this.itemGroupRepository = itemGroupRepository;
        this.itemService = itemService;
        itemGroupMapper = new ItemGroupMapper();
    }


    public List<ItemGroupDTO> createItemGroups(List<CreateItemGroupDTO> orderList, Order order) {
        return orderList.stream()
                .map(createItemGroupDTO -> createItemGroup(createItemGroupDTO, order))
                .toList();
    }

    private ItemGroupDTO createItemGroup(CreateItemGroupDTO createItemGroupDTO, Order order) {
        Item item = itemService.getItemByID(createItemGroupDTO.getItemID());
        int amount = createItemGroupDTO.getAmount();
        ItemGroup itemGroup = itemGroupMapper.mapItemToItemGroup(order, item, amount);
        itemGroupRepository.save(itemGroup);
        return itemGroupMapper.mapItemGroupToDTO(itemGroup);
    }
    public List<ItemGroupDTO> reorderItemGroups(List<ItemGroup> itemGroups, Order order) {
        return itemGroups.stream().
                map(itemGroup -> reorderItemGroup(itemGroup, order))
                .toList();
    }

    private ItemGroupDTO reorderItemGroup(ItemGroup itemGroup, Order order) {
        Item item = itemService.getItemByID(itemGroup.getItemID());
        ItemGroup updatedItemGroup = new ItemGroup(order, item, itemGroup.getAmount());
        itemGroupRepository.save(updatedItemGroup);
        return itemGroupMapper.mapItemGroupToDTO(updatedItemGroup);
    }

    public List<ItemGroup> getItemGroupsForOrder(Order order) {
        return itemGroupRepository.findByOrder(order);
    }

}
