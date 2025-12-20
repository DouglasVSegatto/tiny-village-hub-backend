package com.segatto_builder.tinyvillagehub.model;

import com.segatto_builder.tinyvillagehub.model.enums.ItemAvailabilityType;
import com.segatto_builder.tinyvillagehub.model.enums.ItemStatus;
import com.segatto_builder.tinyvillagehub.model.enums.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemAvailabilityType availabilityType = ItemAvailabilityType.TRADE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemStatus status = ItemStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    private LocalDateTime uploadDate = LocalDateTime.now();

    // Constructor for minimum required object creation
    public Item(String name, String description, ItemType type, User owner) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.owner = owner;
    }
}
