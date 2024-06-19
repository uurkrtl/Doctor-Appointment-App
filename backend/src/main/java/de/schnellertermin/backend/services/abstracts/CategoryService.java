package de.schnellertermin.backend.services.abstracts;

import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.CategoryGetAllResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryGetAllResponse> getAllCategories();
    CategoryCreatedResponse getCategoryById(String id);
    CategoryCreatedResponse addCategory (CategoryRequest categoryRequest);
    CategoryCreatedResponse updateCategory (String id, CategoryRequest categoryRequest);
    void deleteCategory (String id);
}
