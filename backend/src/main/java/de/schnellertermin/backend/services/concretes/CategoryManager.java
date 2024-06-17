package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.services.abstracts.CategoryService;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;

    @Override
    public CategoryCreatedResponse addCategory(CategoryRequest categoryRequest) {
        Category category = modelMapperService.forRequest().map(categoryRequest, Category.class);
        category.setId(idService.generateCategoryId());
        category = categoryRepository.save(category);
        return modelMapperService.forResponse().map(category, CategoryCreatedResponse.class);
    }
}
