package de.schnellertermin.backend.repositories;

import de.schnellertermin.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByName(String categoryName);
}
