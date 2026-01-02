package com.segatto_builder.tinyvillagehub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column
    private String neighborhood;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;


    public String getFullAddress() {
        StringBuilder address = new StringBuilder();

        if (neighborhood != null && !neighborhood.trim().isEmpty()) {
            address.append(neighborhood);
        }

        if (city != null && !city.trim().isEmpty()) {
            if (!address.isEmpty()) address.append(", ");
            address.append(city);
        }

        if (state != null && !state.trim().isEmpty()) {
            if (!address.isEmpty()) address.append(", ");
            address.append(state);
        }

        if (country != null && !country.trim().isEmpty()) {
            if (!address.isEmpty()) address.append(", ");
            address.append(country);
        }

        return address.toString();
    }

    public String getCityState() {
        if (city != null && state != null) {
            return city + ", " + state;
        }
        return city != null ? city : state;
    }


}
