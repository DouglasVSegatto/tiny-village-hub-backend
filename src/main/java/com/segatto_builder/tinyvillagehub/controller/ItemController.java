package com.segatto_builder.tinyvillagehub.controller;

// ... existing imports ...
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

    // ... existing constructor ...

    // ... existing createItem method ...

    /**
     * Endpoint to list all items available for trade or donation.
     * This endpoint is PUBLICLY ACCESSIBLE.
     */
    @GetMapping("/available")
    public List<ItemListingDto> listAvailableItems() {
        List<Item> items = itemService.findAllAvailableItems();

        // Map the Item entities to the safe ItemListingDto list
        return items.stream()
                .map(ItemListingDto::new) // Uses the DTO constructor we created
                .collect(Collectors.toList());
    }

//    @GetMapping("/{itemId}")
//    public ResponseEntity<ItemListingDto> getItemDetails(@)
}