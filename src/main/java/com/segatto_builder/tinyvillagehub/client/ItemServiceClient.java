package com.segatto_builder.tinyvillagehub.client;

import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceCreateDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceUpdateDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemServiceResponseDto;
import com.segatto_builder.tinyvillagehub.mappers.ItemMapper;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    private HttpHeaders createHeadersWithUser(MediaType contentType) {
        User currentUser = authFacade.getCurrentUser();
        HttpHeaders headers = createServiceHeaders();
        headers.set("X-User-Id", currentUser.getId().toString());
        headers.set("X-User-Role", currentUser.getRole().name());
        headers.setContentType(contentType);
        return headers;
    }

    private HttpHeaders createHeadersWithUser(User currentUser, MediaType contentType) {
        HttpHeaders headers = createServiceHeaders();
        headers.set("X-User-Id", currentUser.getId().toString());
        headers.set("X-User-Role", currentUser.getRole().name());
        headers.setContentType(contentType);
        return headers;
    }

    private HttpHeaders createServiceHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Service-Key", serviceKey);
        return headers;
    }


    public void createItem(ItemRequestDto dto) {
        User currentUser = authFacade.getCurrentUser();
        ItemServiceCreateDto createDto = itemMapper.toServiceCreate(dto, currentUser);

        String url = itemsServiceUrl + "/api/items";
        HttpEntity<ItemServiceCreateDto> entity = new HttpEntity<>(createDto, createHeadersWithUser(currentUser, MediaType.APPLICATION_JSON));
        restTemplate.postForObject(url, entity, Void.class);
    }

    public void updateItem(String id, ItemRequestDto dto) {
        ItemServiceUpdateDto updateDto = itemMapper.toServiceUpdate(dto);

        String url = itemsServiceUrl + "/api/items/" + id;
        HttpEntity<ItemServiceUpdateDto> entity = new HttpEntity<>(updateDto, createHeadersWithUser(MediaType.APPLICATION_JSON));
        restTemplate.put(url, entity);
    }

    public void deleteItem(String id) {
        String url = itemsServiceUrl + "/api/items/" + id;
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser(MediaType.APPLICATION_JSON));
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public void updateStatus(String id, String status) {
        String url = itemsServiceUrl + "/api/items/" + id + "/status?status=" + status;
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser(MediaType.APPLICATION_JSON));
        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
    }

    public List<ItemServiceResponseDto> getMyItems() {
        String url = itemsServiceUrl + "/api/items/my-items";
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser(MediaType.APPLICATION_JSON));
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


    // IMAGES
    public String uploadImage(String itemId, MultipartFile file) {
        String url = itemsServiceUrl + "/api/items/" + itemId + "/images";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, createHeadersWithUser(MediaType.MULTIPART_FORM_DATA));
        return restTemplate.postForObject(url, entity, String.class);
    }

    public void deleteImage(String itemId, int index) {
        String url = itemsServiceUrl + "/api/items/" + itemId + "/images/" + index;
        HttpEntity<Void> entity = new HttpEntity<>(createHeadersWithUser(MediaType.MULTIPART_FORM_DATA));
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
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