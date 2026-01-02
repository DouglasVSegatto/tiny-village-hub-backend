package com.segatto_builder.tinyvillagehub.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {

    /**
     * Example JSON:
     {
     "neighborhood": "Vila Madalena",
     "city": "São Paulo",
     "state": "SP",
     "country": "Brazil"
     }
     */

    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

}