package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    /**
     * Endpoint to list all items available for trade or donation.
     * This endpoint is PUBLICLY ACCESSIBLE.
     */
    @GetMapping("/available")
    public List<ItemListingDto> listAvailableItems() {
        List<Item> items = itemService.findAllAvailableItems();
        return items.stream()
                .map(ItemListingDto::new)
                .collect(Collectors.toList());
    }

}