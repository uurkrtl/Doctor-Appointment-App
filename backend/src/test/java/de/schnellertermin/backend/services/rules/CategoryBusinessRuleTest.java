package de.schnellertermin.backend.services.rules;

import de.schnellertermin.backend.core.exceptions.types.DuplicateRecordException;
import de.schnellertermin.backend.core.exceptions.types.RecordNotFoundException;
import de.schnellertermin.backend.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryBusinessRuleTest {
    @InjectMocks
    private CategoryBusinessRule categoryBusinessRule;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfCategoryNameExists_whenCategoryNameExist_shouldThrowDuplicateRecordException() {
        // GIVEN
        String existingCategoryName = "Existing Category";

        // WHEN
        when(categoryRepository.existsByName(existingCategoryName)).thenReturn(true);

        // THEN
        assertThrows(DuplicateRecordException.class, () -> categoryBusinessRule.checkIfCategoryNameExists(existingCategoryName));
    }

    @Test
    void checkIfCategoryIdExists_whenCategoryIdDoesNotExist_shouldThrowRecordNotFoundException() {
        // GIVEN
        String nonExistingCategoryId = "NonExistingId";

        // WHEN
        when(categoryRepository.existsById(nonExistingCategoryId)).thenReturn(false);

        // THEN
        assertThrows(RecordNotFoundException.class, () -> categoryBusinessRule.checkIfCategoryIdExists(nonExistingCategoryId));
    }

}