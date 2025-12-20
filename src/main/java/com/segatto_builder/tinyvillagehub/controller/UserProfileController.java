package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemResponseDto;
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
    public ResponseEntity<List<ItemResponseDto>> getMyItems() {
        return ResponseEntity.ok(itemService.findAllByOwnerId());
    }
}