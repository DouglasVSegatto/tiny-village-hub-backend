package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemResponseDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
    public List<ItemResponseDto> listAvailableItems() {
        List<Item> items = itemService.findAllAvailable();
        return items.stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping
    public ResponseEntity<?> create(@RequestBody ItemRequestDto dto) {
        itemService.create(dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ItemRequestDto dto){
        itemService.update(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        itemService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Publish item (INACTIVE/PENDING → ACTIVE)
    @PatchMapping("/{itemId}/activate")
    public ResponseEntity<?> activateItem(@PathVariable UUID itemId) {
        itemService.activateItem(itemId);
        return ResponseEntity.ok().build();
    }

    // Pending Transaction item (ACTIVE → PENDING})
    @PatchMapping("/{itemId}/pending")
    public ResponseEntity<?> pendingItem(@PathVariable UUID itemId) {
        itemService.pendingItem(itemId);
        return ResponseEntity.ok().build();
    }

    // Unpublish/deactivate item (AVAILABLE/PENDING → INACTIVE)
    @PatchMapping("/{itemId}/deactivate")
    public ResponseEntity<?> deactivateItem(@PathVariable UUID itemId) {
        itemService.deactivateItem(itemId);
        return ResponseEntity.ok().build();
    }

    // Complete item (AVAILABLE/PENDING → COMPLETED)
    @PatchMapping("/{itemId}/complete")
    public ResponseEntity<?> completeItem(@PathVariable UUID itemId) {
        itemService.completeItem(itemId);
        return ResponseEntity.ok().build();
    }

}