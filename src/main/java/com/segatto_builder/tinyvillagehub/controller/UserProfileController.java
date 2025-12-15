package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemUploadDto;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import com.segatto_builder.tinyvillagehub.service.ItemService;
import com.segatto_builder.tinyvillagehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final ItemService itemService;

    @GetMapping("/my-items")
    public ResponseEntity<List<ItemListingDto>> getMyItems() {
        return ResponseEntity.ok(itemService.findAllByUserId());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemListingDto> updateItem(
            @PathVariable UUID itemId,
            @RequestPart("itemDetails") ItemUploadDto itemDto) throws IOException {

        return ResponseEntity.ok(itemService.updateItem(itemId, itemDto));
    }


    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID itemId) throws IOException {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}