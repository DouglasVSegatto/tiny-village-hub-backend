package com.segatto_builder.tinyvillagehub.dto.item;

import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemServiceResponseDto {

    /**
     * DTO for receiving responses from Item-Service microservice.
     * Uses String id to match Item-Service's 18-byte format.
     */

    private String id;
    private String name;
    private String description;
    private ItemType type;
    private String imageUrl;
    private ItemStatus status;
    private String ownerUsername;
}
