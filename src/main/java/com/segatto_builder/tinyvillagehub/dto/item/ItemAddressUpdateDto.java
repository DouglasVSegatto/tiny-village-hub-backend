package com.segatto_builder.tinyvillagehub.dto.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemAddressUpdateDto {
    @NotBlank
    private String neighbourhood;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
}
