package com.segatto_builder.tinyvillagehub.model;

import jakarta.persistence.*;
import lombok.*;
import com.segatto_builder.tinyvillagehub.model.Item;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // NOTE: This field is named passwordHash, but the getter is used by Spring Security
    // Store the secure hash of the password
    @Column(nullable = false)
    private String passwordHash;

    private LocalDateTime joinDate = LocalDateTime.now();

    // Relationship: One User can own Many Items
    // The 'mappedBy' attribute refers to the 'owner' field in the Item entity
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

}