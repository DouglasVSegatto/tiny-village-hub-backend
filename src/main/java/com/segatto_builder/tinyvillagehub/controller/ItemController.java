package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;


    /**
     * Endpoint to list all items available for trade or donation.
     * This endpoint is PUBLICLY ACCESSIBLE.
     */
    @GetMapping("/available")
    public List<ItemListingDto> listAvailableItems() {
        List<Item> items = itemService.findAllAvailable();
        return items.stream()
                .map(ItemListingDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/new-item")
    public ResponseEntity<?> newItem(@RequestBody ItemRequestDto dto) {
        itemService.add(dto);
        return ResponseEntity.noContent().build();
    }
}