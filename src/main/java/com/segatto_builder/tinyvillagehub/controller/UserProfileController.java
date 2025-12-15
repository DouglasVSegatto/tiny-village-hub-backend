package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final IItemService itemService;

    @GetMapping("/my-items")
    public ResponseEntity<List<ItemListingDto>> getMyItems() {
        return ResponseEntity.ok(itemService.findAllByUserId());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemListingDto> updateItem(
            @PathVariable UUID itemId,
            @RequestBody ItemRequestDto itemDto) {

        return ResponseEntity.ok(itemService.updateItem(itemId, itemDto));
    }


    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}