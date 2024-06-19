package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.exceptions.types.RecordNotFoundException;
import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.services.abstracts.CategoryService;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.CategoryGetAllResponse;
import de.schnellertermin.backend.services.messages.CategoryMessage;
import de.schnellertermin.backend.services.rules.CategoryBusinessRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;
    public final CategoryBusinessRule categoryBusinessRule;

    @Override
    public List<CategoryGetAllResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapperService.forResponse().map(category, CategoryGetAllResponse.class)).toList();
    }

    @Override
    public CategoryCreatedResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CategoryMessage.CATEGORY_NOT_FOUND));
        return modelMapperService.forResponse().map(category, CategoryCreatedResponse.class);
    }

    @Override
    public CategoryCreatedResponse addCategory(CategoryRequest categoryRequest) {
        categoryBusinessRule.checkIfCategoryNameExists(categoryRequest.getName());
        Category category = modelMapperService.forRequest().map(categoryRequest, Category.class);
        category.setId(idService.generateCategoryId());
        category = categoryRepository.save(category);
        return modelMapperService.forResponse().map(category, CategoryCreatedResponse.class);
    }

    @Override
    public CategoryCreatedResponse updateCategory(String id, CategoryRequest categoryRequest) {
        categoryBusinessRule.checkIfCategoryIdExists(id);
        Category updatedCategory = modelMapperService.forRequest().map(categoryRequest, Category.class);
        updatedCategory.setId(id);
        updatedCategory = categoryRepository.save(updatedCategory);
        return modelMapperService.forResponse().map(updatedCategory, CategoryCreatedResponse.class);
    }

    @Override
    public void deleteCategory(String id) {
        categoryBusinessRule.checkIfCategoryIdExists(id);
        categoryRepository.deleteById(id);
    }
}
