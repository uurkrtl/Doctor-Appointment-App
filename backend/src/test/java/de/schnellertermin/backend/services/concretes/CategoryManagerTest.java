package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import de.schnellertermin.backend.services.rules.CategoryBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryManagerTest {
    @InjectMocks
    private CategoryManager categoryManager;
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @Mock
    @SuppressWarnings("unused")
    private CategoryBusinessRule categoryBusinessRule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void addCategory_whenRequestIsValid_returnCategoryCreatedResponse() {
        // GIVEN
        CategoryRequest categoryRequest = CategoryRequest.builder().name("Test Category").build();
        CategoryCreatedResponse expectedResponse = CategoryCreatedResponse.builder().name("Test Category").build();
        Category category = Category.builder().build();

        // WHEN
        when(idService.generateCategoryId()).thenReturn("1");
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(categoryRequest, Category.class)).thenReturn(category);
        when(modelMapper.map(category, CategoryCreatedResponse.class)).thenReturn(expectedResponse);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryCreatedResponse actualResponse = categoryManager.addCategory(categoryRequest);

        // THEN
        verify(categoryRepository, times(1)).save(category);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }
}