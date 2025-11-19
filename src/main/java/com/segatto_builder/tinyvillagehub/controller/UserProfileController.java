package com.segatto_builder.tinyvillagehub.controller;

import com.segatto_builder.tinyvillagehub.dto.item.ItemUploadDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemListingDto;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.service.ItemService;
import com.segatto_builder.tinyvillagehub.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;
    private final ItemService itemService;

    /**
     * Fetches all items created by the currently authenticated user.
     * Requires a valid JWT (Authorization: Bearer <token>)
     *
     * @param userDetails The UserDetails object provided by Spring Security after JWT validation.
     */
    @GetMapping("/my-items")
    public ResponseEntity<List<ItemListingDto>> getMyItems(@AuthenticationPrincipal UserDetails userDetails) {

        // 1. Get the authenticated username
        String username = userDetails.getUsername();

        // 2. Find the full User entity from the database
        User currentUser = userService.findByUsername(username);

        // 3. Find items owned by that User
        List<Item> ownedItems = userService.findItemsByOwner(currentUser);

        // 4. Map entities to DTOs for response
        List<ItemListingDto> response = ownedItems.stream()
                .map(ItemListingDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemListingDto> updateItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long itemId,
            @RequestPart("itemDetails") ItemUploadDto itemDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        try {
            User currentUser = userService.findByUsername(userDetails.getUsername());

            // Service handles authorization and update logic
            Item updatedItem = itemService.updateItem(itemId, currentUser, itemDto, file);

            return ResponseEntity.ok(new ItemListingDto(updatedItem));

        } catch (SecurityException e) {
            // Thrown if the user tries to update an item they don't own
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long itemId) {

        try {
            User currentUser = userService.findByUsername(userDetails.getUsername());

            // Service handles authorization and deletion
            itemService.deleteItem(itemId, currentUser);

            // Return 204 No Content for successful deletion
            return ResponseEntity.noContent().build();

        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            // Item was not found
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}