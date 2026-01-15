package com.segatto_builder.tinyvillagehub.client;

import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemResponseDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceRequestDto;
import com.segatto_builder.tinyvillagehub.mappers.ItemMapper;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.model.enums.UserRole;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceClient {
    private final RestTemplate restTemplate;

    @Value("${items-service.url}")
    private String itemsServiceUrl;

    @Value("${service.security.key}")
    private String serviceKey;
    
    private final IAuthFacade authFacade;
    private final ItemMapper itemMapper;

    private HttpHeaders createHeaders() {
        User currentUser = authFacade.getCurrentUser();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", currentUser.getId().toString());
        headers.set("X-User-Role", currentUser.getRole().name());
        headers.set("X-Service-Key", serviceKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public void createItem(ItemRequestDto dto) {
        User currentUser = authFacade.getCurrentUser();
        ItemServiceRequestDto serviceDto = itemMapper.toServiceRequest(dto);
        itemMapper.enrichWithOwner(serviceDto, currentUser);

        String url = itemsServiceUrl + "/api/items";
        HttpEntity<ItemServiceRequestDto> entity = new HttpEntity<>(serviceDto, createHeaders());
        restTemplate.postForObject(url, entity, Void.class);
    }

    public void updateItem(String id, ItemRequestDto dto) {
        String url = itemsServiceUrl + "/api/items/" + id;
        HttpEntity<ItemRequestDto> entity = new HttpEntity<>(dto, createHeaders());
        restTemplate.put(url, entity);
    }

    public void deleteItem(String id) {
        String url = itemsServiceUrl + "/api/items/" + id;
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public void updateStatus(String id, String status) {
        String url = itemsServiceUrl + "/api/items/" + id + "/status?status=" + status;
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    public List<ItemResponseDto> getMyItems() {
        String url = itemsServiceUrl + "/api/items/my-items";
        HttpEntity<Void> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<ItemResponseDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ItemResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public ItemResponseDto getItem(String id) {
        String url = itemsServiceUrl + "/api/items/" + id;
        return restTemplate.getForObject(url, ItemResponseDto.class);
    }

    public List<ItemResponseDto> getActiveItems() {
        String url = itemsServiceUrl + "/api/items";
        ResponseEntity<ItemResponseDto[]> response = restTemplate.getForEntity(url, ItemResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    // Public search endpoints (no auth needed)
    public List<ItemResponseDto> searchByNeighborhood(String neighborhood) {
        String url = itemsServiceUrl + "/api/items/search/neighbourhood/" + neighborhood;
        ResponseEntity<ItemResponseDto[]> response = restTemplate.getForEntity(url, ItemResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ItemResponseDto> searchByCity(String city) {
        String url = itemsServiceUrl + "/api/items/search/city/" + city;
        ResponseEntity<ItemResponseDto[]> response = restTemplate.getForEntity(url, ItemResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ItemResponseDto> searchByState(String state) {
        String url = itemsServiceUrl + "/api/items/search/state/" + state;
        ResponseEntity<ItemResponseDto[]> response = restTemplate.getForEntity(url, ItemResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ItemResponseDto> searchByCountry(String country) {
        String url = itemsServiceUrl + "/api/items/search/country/" + country;
        ResponseEntity<ItemResponseDto[]> response = restTemplate.getForEntity(url, ItemResponseDto[].class);
        return Arrays.asList(response.getBody());
    }
}