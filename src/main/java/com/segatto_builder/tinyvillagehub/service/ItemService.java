package com.segatto_builder.tinyvillagehub.service;

import com.segatto_builder.tinyvillagehub.dto.item.ItemRequestDto;
import com.segatto_builder.tinyvillagehub.dto.item.ItemResponseDto;
import com.segatto_builder.tinyvillagehub.mappers.ItemMapper;
import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import com.segatto_builder.tinyvillagehub.repository.ItemRepository;
import com.segatto_builder.tinyvillagehub.security.IAuthFacade;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {

    private final ItemRepository itemRepository;
    private final IAuthFacade authFacade;
    private final ItemMapper itemMapper;

    //USER RELATED - TODO improve as it goes.
    @Override
    public List<ItemResponseDto> findAllByOwnerId() {
        UUID ownerId = authFacade.getCurrentUserId();
        List<Item> items = findOwnerItems(ownerId);
        return items.stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findActiveByOwnerId() {
        UUID owner_id = authFacade.getCurrentUserId();
        return findOwnerItemsByStatus(owner_id, ItemStatus.ACTIVE);
    }

    @Override
    public List<Item> findAllAvailable() {
        return itemRepository.findByStatus(ItemStatus.ACTIVE);
    }

    @Override
    public Item findById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + itemId));
    }

    @Override
    public void delete(UUID itemId) {
        Item item = findById(itemId);
        validateOwnership(item);
        log.info("DELETED item by user {} (itemId: {})", item.getOwner().getUsername(), item.getId());
        itemRepository.delete(item);
    }

    @Override
    public void update(UUID id, ItemRequestDto dto) {

        Item item = findById(id);
        validateOwnership(item);

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setType(dto.getType());
        item.setAvailabilityType(dto.getAvailabilityType());
        itemRepository.save(item);
        log.info("UPDATED item by user {} (itemId: {})", item.getOwner().getUsername(), item.getId());
    }

    @Override
    public void create(ItemRequestDto dto) {
        Item item = itemMapper.toModel(dto);
        User user = authFacade.getCurrentUser();
        item.setOwner(user);
        //All Items start as INACTIVE, user can update it later.
        item.setStatus(ItemStatus.INACTIVE);
        itemRepository.save(item);
        log.info("CREATED item by user {} (itemId: {})", user.getUsername(), item.getId());
    }

    @Override
    public void activateItem(UUID id) {
        Item item = findById(id);
        validateOwnership(item);

        Set<ItemStatus> allowedStates = Set.of(ItemStatus.INACTIVE, ItemStatus.PENDING);

        if (!allowedStates.contains(item.getStatus())) {
            throw new IllegalStateException(
                    String.format("Cannot activate item with status %s. Allowed states: %s",
                            item.getStatus(), allowedStates)
            );
        }

        item.setStatus(ItemStatus.ACTIVE);
        itemRepository.save(item);
        log.info("ACTIVATED item by user {} (itemId: {})", item.getOwner().getUsername(), item.getId());
    }

    @Override
    public void deactivateItem(UUID id) {
        Item item = findById(id);
        validateOwnership(item);

        Set<ItemStatus> allowedStates = Set.of(ItemStatus.ACTIVE, ItemStatus.PENDING);

        if (!allowedStates.contains(item.getStatus())) {
            throw new IllegalStateException(
                    String.format("Cannot deactivate item with status %s. Allowed states: %s",
                            item.getStatus(), allowedStates)
            );
        }

        item.setStatus(ItemStatus.INACTIVE);
        itemRepository.save(item);
        log.info("DEACTIVATED item by user {} (itemId: {})", item.getOwner().getUsername(), item.getId());
    }

    @Override
    public void pendingItem(UUID id) {
        Item item = findById(id);
        validateOwnership(item);

        if (item.getStatus() != ItemStatus.ACTIVE) {
            throw new IllegalStateException(
                    String.format("Cannot set item to pending with status %s. Must be ACTIVE",
                            item.getStatus())
            );
        }

        item.setStatus(ItemStatus.PENDING);
        itemRepository.save(item);
        log.info("PENDING item by user {} (itemId: {})", item.getOwner().getUsername(), item.getId());
    }

    @Override
    public List<ItemResponseDto> listByCity(String city) {
        List<Item> items = findByCity(city);
        return items.stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemResponseDto> listByNeighborhood(String neighborhood) {
        List<Item> items = findByNeighborhood(neighborhood);
        return items.stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemResponseDto> listByState(String state) {
        List<Item> items = findByState(state);
        return items.stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemResponseDto> listByCountry(String country) {
        List<Item> items = findByCountry(country);
        return items.stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void completeItem(UUID id) {
        Item item = findById(id);
        validateOwnership(item);

        Set<ItemStatus> allowedStates = Set.of(ItemStatus.ACTIVE, ItemStatus.PENDING);

        if (!allowedStates.contains(item.getStatus())) {
            throw new IllegalStateException(
                    String.format("Cannot complete item with status %s. Allowed states: %s",
                            item.getStatus(), allowedStates)
            );
        }

        item.setStatus(ItemStatus.COMPLETED);
        itemRepository.save(item);
        log.info("COMPLETED item by user {} (itemId: {})", item.getOwner().getUsername(), item.getId());
    }

    //PRIVATE
    private List<Item> findOwnerItems(UUID ownerId) {
        return itemRepository.findByOwnerId(ownerId);
    }

    private List<Item> findOwnerItemsByStatus(UUID ownerId, ItemStatus status) {
        return itemRepository.findByOwnerIdAndStatus(ownerId, status);
    }

    private void validateOwnership(Item item) {
        User user = authFacade.getCurrentUser();

        if (!item.getOwner().getId().equals(user.getId())) {
            log.warn("Unauthorized access attempt to item {} by user {}", item.getId(), user.getUsername());
            throw new SecurityException("User is not authorized to update this item.");
        }
    }

    private List<Item> findByCity(String city) {
        return itemRepository.findByStatusAndOwnerAddressCityIgnoreCase(ItemStatus.ACTIVE, city);
    }

    private List<Item> findByNeighborhood(String neighborhood) {
        return itemRepository.findByStatusAndOwnerAddressNeighborhoodIgnoreCase(ItemStatus.ACTIVE, neighborhood);
    }

    private List<Item> findByState(String neighborhood) {
        return itemRepository.findByStatusAndOwnerAddressStateIgnoreCase(ItemStatus.ACTIVE, neighborhood);
    }

    private List<Item> findByCountry(String neighborhood) {
        return itemRepository.findByStatusAndOwnerAddressCountryIgnoreCase(ItemStatus.ACTIVE, neighborhood);
    }

}