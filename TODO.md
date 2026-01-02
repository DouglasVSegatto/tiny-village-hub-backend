# TinyVillageHub - TODO List

## Basic Item Location Search (Current Branch: feature/basic-item-location-search)

### 1. Address Entity ✅ COMPLETED

- [x] Create `Address.java` as `@Embeddable` entity
    - `String country` - for global scaling
    - `String state` - state/province level
    - `String city` - primary search filter
    - `String neighborhood` - proximity context
- [x] Add utility methods:
    - `getFullAddress()` - formatted display: "Neighborhood, City, State, Country"
    - `getCityState()` - common display: "City, State"
- [x] Add `@Getter @Setter` Lombok annotations

### 2. User Entity Updates ✅ COMPLETED

- [x] Add `@Embedded Address address` field to User.java
- [x] Set `@Column(nullable = false)` since location required for search
- [x] Update UserRegistrationDto with address fields
- [x] Create AddressRequestDto for address-only operations
- [x] Create AddressMapper with dual methods (registration + standalone)
- [x] Add address update endpoint in UserController
- [x] Update UserService with address functionality

### 3. Location-Based Search Features 🚧 NEXT PHASE

- [ ] Add repository methods for location search:
    - Find items by neighborhood
    - Find items by city
    - Find items by state
    - Find items by country
    - Find items by user's location area
- [ ] Create search endpoints in ItemController:
    - `GET /api/items/search/neighborhood/{neighborhood}`
    - `GET /api/items/search/city/{city}`
    - `GET /api/items/search/state/{state}`
    - `GET /api/items/search/country/{country}`
- [ ] Update ItemService with location search methods
- [ ] Add location-based filtering to existing item endpoints

### 4. Search Functionality Implementation

- [ ] Neighborhood-level search: "Show items in Vila Madalena"
- [ ] City-level search: "Show all items in São Paulo"
- [ ] State-level search: "Show items in SP state"
- [ ] Country-level search: "Show items in Brazil"
- [ ] Display location context in item listings
- [ ] Add location-based sorting (closest first)

### 5. Advanced Location Features (Future)

- [ ] "Items near me" based on current user's address
- [ ] Cross-location search (city + neighboring areas)
- [ ] Location-based item recommendations
- [ ] Distance calculation and display

---

## Completed Features ✅

- ✅ Complete item CRUD operations
- ✅ Enterprise-grade status transitions (INACTIVE/ACTIVE/PENDING/COMPLETED)
- ✅ JWT authentication with refresh tokens
- ✅ Multiple refresh tokens per user support
- ✅ Global exception handling
- ✅ Ownership validation for all item operations
- ✅ Address entity with embedded structure
- ✅ User registration with address capture
- ✅ Address update functionality
- ✅ AddressMapper with dual mapping methods
- ✅ UserMapper for clean DTO conversions