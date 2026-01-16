package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.client.ItemServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/items/{itemId}/images")
@RequiredArgsConstructor
public class ImageController {

    private final ItemServiceClient itemServiceClient;

    @PostMapping
    public ResponseEntity<String> uploadImage(
            @PathVariable String itemId,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = itemServiceClient.uploadImage(itemId, file);
        return ResponseEntity.ok(imageUrl);
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable String itemId,
            @PathVariable int index) {
        itemServiceClient.deleteImage(itemId, index);
        return ResponseEntity.noContent().build();
    }
}

