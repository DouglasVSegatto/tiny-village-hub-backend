package com.segatto_builder.tinyvillagehub.repository;

import com.segatto_builder.tinyvillagehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA automatically generates the SQL for this method
    Optional<User> findByUsername(String username);
}