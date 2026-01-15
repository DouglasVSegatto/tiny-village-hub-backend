package com.segatto_builder.tinyvillagehub.client;

import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceResponseDto;
import com.segatto_builder.tinyvillagehub.mappers.ItemMapper;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceClient {
    private final RestTemplate restTemplate;
    private final IAuthFacade authFacade;
    private final ItemMapper itemMapper;
    @Value("${items-service.url}")
    private String itemsServiceUrl;
    @Value("${service.security.key}")
    private String serviceKey;

    private HttpHeaders createHeadersWithUser() {
        User currentUser = authFacade.getCurrentUser();
        HttpHeaders headers = createServiceHeaders();
        headers.set("X-User-Id", currentUser.getId().toString());
        headers.set("X-User-Role", currentUser.getRole().name());
        return headers;
    }

    private HttpHeaders createHeadersWithUser(User currentUser) {
        HttpHeaders headers = createServiceHeaders();
        headers.set("X-User-Id", currentUser.getId().toString());
        headers.set("X-User-Role", currentUser.getRole().name());
        return headers;
    }

    private HttpHeaders createServiceHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Service-Key", serviceKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public void createItem(ItemRequestDto dto) {
        User currentUser = authFacade.getCurrentUser();
        ItemServiceRequestDto serviceDto = itemMapper.toServiceRequest(dto, currentUser);

        String url = itemsServiceUrl + "/api/items";
        HttpEntity<ItemServiceRequestDto> entity = new HttpEntity<>(serviceDto, createHeadersWithUser());
        restTemplate.postForObject(url, entity, Void.class);
    }

    public void updateItem(String id, ItemRequestDto dto) {
        User currentUser = authFacade.getCurrentUser();
        ItemServiceRequestDto serviceDto = itemMapper.toServiceRequest(dto, currentUser);
        itemMapper.enrichWithOwner(serviceDto, currentUser);

        String url = itemsServiceUrl + "/api/items/" + id;
        HttpEntity<ItemServiceRequestDto> entity = new HttpEntity<>(serviceDto, createHeadersWithUser(currentUser));
        restTemplate.put(url, entity);
    }

    public void deleteItem(String id) {
        String url = itemsServiceUrl + "/api/items/" + id;
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser());
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public void updateStatus(String id, String status) {
        String url = itemsServiceUrl + "/api/items/" + id + "/status?status=" + status;
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser());
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    public List<ItemServiceResponseDto> getMyItems() {
        String url = itemsServiceUrl + "/api/items/my-items";
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser());
        ResponseEntity<ItemServiceResponseDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ItemServiceResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public ItemServiceResponseDto getItem(String id) {
        String url = itemsServiceUrl + "/api/items/" + id;
        return restTemplate.getForObject(url, ItemServiceResponseDto.class);
    }

    public List<ItemServiceResponseDto> getActiveItems() {
        String url = itemsServiceUrl + "/api/items";
        HttpEntity<Void> entity = new HttpEntity<>(createServiceHeaders());
        ResponseEntity<ItemServiceResponseDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ItemServiceResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    // Public search endpoints (no auth needed)
    public List<ItemServiceResponseDto> searchByNeighborhood(String neighborhood) {
        String url = itemsServiceUrl + "/api/items/search/neighbourhood/" + neighborhood;
        ResponseEntity<ItemServiceResponseDto[]> response = restTemplate.getForEntity(url, ItemServiceResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ItemServiceResponseDto> searchByCity(String city) {
        String url = itemsServiceUrl + "/api/items/search/city/" + city;
        ResponseEntity<ItemServiceResponseDto[]> response = restTemplate.getForEntity(url, ItemServiceResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ItemServiceResponseDto> searchByState(String state) {
        String url = itemsServiceUrl + "/api/items/search/state/" + state;
        ResponseEntity<ItemServiceResponseDto[]> response = restTemplate.getForEntity(url, ItemServiceResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    public List<ItemServiceResponseDto> searchByCountry(String country) {
        String url = itemsServiceUrl + "/api/items/search/country/" + country;
        ResponseEntity<ItemServiceResponseDto[]> response = restTemplate.getForEntity(url, ItemServiceResponseDto[].class);
        return Arrays.asList(response.getBody());
    }
}