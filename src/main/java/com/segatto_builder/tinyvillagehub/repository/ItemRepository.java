package com.segatto_builder.tinyvillagehub.repository;

import com.segatto_builder.tinyvillagehub.model.Item;
import com.segatto_builder.tinyvillagehub.model.User;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus; // <-- Ensure this is imported
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Spring Data JPA automatically creates the query: SELECT * FROM items WHERE status = ?
    List<Item> findByStatus(ItemStatus status);

    Optional<Item> findById(UUID id);
    List<Item> findByOwnerId(UUID ownerId);
    List<Item> findByOwnerIdAndStatus(UUID ownerId, ItemStatus status);

}
