package de.schnellertermin.backend.services.rules;

import de.schnellertermin.backend.core.exceptions.types.DuplicateRecordException;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.services.messages.CategoryMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBusinessRule {
    private final CategoryRepository categoryRepository;

    public void checkIfCategoryNameExists(String categoryName) {
        if(categoryRepository.existsByName(categoryName)) {
            throw new DuplicateRecordException(CategoryMessage.CATEGORY_NAME_EXISTS);
        }
    }
}
