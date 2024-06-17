package de.schnellertermin.backend.services.abstracts;

import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;

public interface CategoryService {
    CategoryCreatedResponse addCategory (CategoryRequest categoryRequest);
}
