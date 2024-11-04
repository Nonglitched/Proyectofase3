package com.nutrition.store.nutrition_store.repository;

import com.nutrition.store.nutrition_store.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {
    Optional<user> findByEmail(String email);
}
