package com.segatto_builder.tinyvillagehub.model;

import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    // Use an Enum for Type (e.g., BOOK, TOY, PICTURE) for strong type checking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType type;

    // True if the item is primarily offered for trade
    private boolean isForTrade = true;

    // True if the item is also offered for donation
    private boolean isForDonation = false;

    // Status of the item (e.g., AVAILABLE, PENDING_TRADE, TRADED, DONATED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status = ItemStatus.AVAILABLE;

    // Relationship: Many Items belong to One User
    // Use @ManyToOne to establish the foreign key relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    private LocalDateTime uploadDate = LocalDateTime.now();

    @Column(nullable = true, length = 255)
    private String imageUrl;


    // Constructor for easy object creation
    public Item(String name, String description, ItemType type, User owner) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.owner = owner;
    }
}
