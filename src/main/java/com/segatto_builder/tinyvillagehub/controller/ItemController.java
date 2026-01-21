package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.client.ItemServiceClient;
import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceClient itemServiceClient;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ItemRequestDto dto) {
        itemServiceClient.createItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemServiceResponseDto> getItem(@PathVariable String id) {
        ItemServiceResponseDto item = itemServiceClient.getItem(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ItemRequestDto dto) {
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

    @GetMapping("/my-items")
    public ResponseEntity<List<ItemServiceResponseDto>> getMyItems() {
        List<ItemServiceResponseDto> items = itemServiceClient.getMyItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/available")
    public List<ItemServiceResponseDto> listAvailableItems() {
        return itemServiceClient.getActiveItems();
    }

    @GetMapping("/search/neighbourhood/{neighbourhood}")
    public ResponseEntity<List<ItemServiceResponseDto>> getItemsByNeighbourhood(@PathVariable String neighbourhood) {
        List<ItemServiceResponseDto> items = itemServiceClient.searchByNeighborhood(neighbourhood);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/city/{city}")
    public ResponseEntity<List<ItemServiceResponseDto>> getItemsByCity(@PathVariable String city) {
        List<ItemServiceResponseDto> items = itemServiceClient.searchByCity(city);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/state/{state}")
    public ResponseEntity<List<ItemServiceResponseDto>> getItemsByState(@PathVariable String state) {
        List<ItemServiceResponseDto> items = itemServiceClient.searchByState(state);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/country/{country}")
    public ResponseEntity<List<ItemServiceResponseDto>> getItemsByCountry(@PathVariable String country) {
        List<ItemServiceResponseDto> items = itemServiceClient.searchByCountry(country);
        return ResponseEntity.ok(items);
    }

    // PAGINATED SEARCH ENDPOINTS (Public)
    @GetMapping("/search/paginated")
    public ResponseEntity<Object> getActiveItemsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemServiceClient.getActiveItemsPaginated(page, size));
    }

    @GetMapping("/search/paginated/neighbourhood/{neighbourhood}")
    public ResponseEntity<Object> getItemsByNeighbourhoodPaginated(
            @PathVariable String neighbourhood,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemServiceClient.searchByNeighborhoodPaginated(neighbourhood, page, size));
    }

    @GetMapping("/search/paginated/city/{city}")
    public ResponseEntity<Object> getItemsByCityPaginated(
            @PathVariable String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemServiceClient.searchByCityPaginated(city, page, size));
    }

    @GetMapping("/search/paginated/state/{state}")
    public ResponseEntity<Object> getItemsByStatePaginated(
            @PathVariable String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemServiceClient.searchByStatePaginated(state, page, size));
    }

    @GetMapping("/search/paginated/country/{country}")
    public ResponseEntity<Object> getItemsByCountryPaginated(
            @PathVariable String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemServiceClient.searchByCountryPaginated(country, page, size));
    }

    // PAGINATED USER ENDPOINTS (Authenticated)
    @GetMapping("/my-items/paginated")
    public ResponseEntity<Object> getMyItemsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(itemServiceClient.getMyItemsPaginated(page, size));
    }

}