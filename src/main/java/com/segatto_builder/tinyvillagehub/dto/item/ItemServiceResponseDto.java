package com.segatto_builder.tinyvillagehub.dto.item;

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
    private String type;
    private String imageUrl;
    private String status;
    private String ownerUsername;
}
