package com.switchfully.eurder.service.itemgroup;

import com.switchfully.eurder.domain.itemgroup.ItemGroupRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ItemGroupService {
    private final ItemGroupRepository itemGroupRepository;
    private final ItemGroupMapper itemGroupMapper;

    public ItemGroupService(ItemGroupRepository itemGroupRepository) {
        this.itemGroupRepository = itemGroupRepository;
        itemGroupMapper = new ItemGroupMapper();
    }


}
