package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.customer.Feature;
import com.switchfully.eurder.service.item.ItemService;
import com.switchfully.eurder.service.item.dto.CreateItemDTO;
import com.switchfully.eurder.service.item.dto.ItemDTO;
import com.switchfully.eurder.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
public class ItemController {

    private final SecurityService securityService;
    private final ItemService itemService;
    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    public ItemController(SecurityService securityService, ItemService itemService) {
        this.securityService = securityService;
        this.itemService = itemService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO addNewItemToStock(@RequestHeader String authorization, @RequestBody CreateItemDTO createItemDTO) {
        securityService.validateAuthorization(authorization, Feature.CREATE_ITEM);
        log.debug("request to add new item to stock");
        return itemService.addNewItemToStock(createItemDTO);
    }

}
