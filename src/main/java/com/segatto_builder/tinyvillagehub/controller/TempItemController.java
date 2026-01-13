package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.client.ItemServiceClient;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemResponseDto;
import com.segatto_builder.tinyvillagehub.model.enums.UserRole;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items/temp")
@RequiredArgsConstructor
public class TempItemController {

    private final ItemServiceClient itemServiceClient;
    private final IAuthFacade authFacade;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ItemRequestDto dto) {
        itemServiceClient.createItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable String id) {
        ItemResponseDto item = itemServiceClient.getItem(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id,@RequestBody ItemRequestDto dto) {
        
        itemServiceClient.updateItem(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        
        itemServiceClient.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam String status) {
        
        itemServiceClient.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getActiveItems() {
        List<ItemResponseDto> items = itemServiceClient.getActiveItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/my-items")
    public ResponseEntity<List<ItemResponseDto>> getMyItems() {
        
        List<ItemResponseDto> items = itemServiceClient.getMyItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/neighbourhood/{neighbourhood}")
    public ResponseEntity<List<ItemResponseDto>> getItemsByNeighbourhood(@PathVariable String neighbourhood) {
        List<ItemResponseDto> items = itemServiceClient.searchByNeighborhood(neighbourhood);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/city/{city}")
    public ResponseEntity<List<ItemResponseDto>> getItemsByCity(@PathVariable String city) {
        List<ItemResponseDto> items = itemServiceClient.searchByCity(city);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/state/{state}")
    public ResponseEntity<List<ItemResponseDto>> getItemsByState(@PathVariable String state) {
        List<ItemResponseDto> items = itemServiceClient.searchByState(state);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/country/{country}")
    public ResponseEntity<List<ItemResponseDto>> getItemsByCountry(@PathVariable String country) {
        List<ItemResponseDto> items = itemServiceClient.searchByCountry(country);
        return ResponseEntity.ok(items);
    }
}