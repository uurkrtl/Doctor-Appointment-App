package de.schnellertermin.backend.services.concretes;

import de.schnellertermin.backend.core.exceptions.types.RecordNotFoundException;
import de.schnellertermin.backend.core.mappers.ModelMapperService;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.services.abstracts.IdService;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import de.schnellertermin.backend.services.dtos.responses.CategoryCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.CategoryGetAllResponse;
import de.schnellertermin.backend.services.rules.CategoryBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import java.util.List;

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
    void getAllCategories_shouldReturnsListOfCategories() {
        // GIVEN
        List<Category> categories = List.of(
                Category.builder().id("1").build(),
                Category.builder().id("2").build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryGetAllResponse> actualResponse = categoryManager.getAllCategories();

        // THEN
        assertEquals(2, actualResponse.size());
    }

    @Test
    void getCategoryById_whenCategoryExists_shouldReturnCategory() {
        // GIVEN
        Category category = Category.builder().id("1").build();
        CategoryCreatedResponse expectedResponse = CategoryCreatedResponse.builder().id("1").build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        when(modelMapperService.forResponse().map(category, CategoryCreatedResponse.class)).thenReturn(expectedResponse);

        CategoryCreatedResponse actualResponse = categoryManager.getCategoryById("1");

        // THEN
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void getCategoryById_whenCategoryDoesNotExist_shouldReturnNull() {
        // GIVEN & WHEN
        when(categoryRepository.findById("1")).thenReturn(Optional.empty());

        // THEN
        assertThrows(RecordNotFoundException.class, () -> categoryManager.getCategoryById("1"));
    }

    @Test
    void addCategory_whenRequestIsValid_shouldReturnCategoryCreatedResponse() {
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

    @Test
    void updateCategory_whenRequestIsValid_shouldReturnCategoryCreatedResponse() {
        // GIVEN
        CategoryRequest categoryRequest = CategoryRequest.builder().name("Test Category").build();
        CategoryCreatedResponse expectedResponse = CategoryCreatedResponse.builder().name("Test Category").build();
        Category category = Category.builder().build();

        // WHEN
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(categoryRequest, Category.class)).thenReturn(category);
        when(modelMapper.map(category, CategoryCreatedResponse.class)).thenReturn(expectedResponse);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryCreatedResponse actualResponse = categoryManager.updateCategory("1", categoryRequest);

        // THEN
        verify(categoryRepository, times(1)).save(category);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void deleteCategory_whenCategoryIdExists_shouldDeleteCategory() {
        // GIVEN
        String existingCategoryId = "ExistingId";

        // WHEN
        when(categoryRepository.existsById(existingCategoryId)).thenReturn(true);
        categoryManager.deleteCategory(existingCategoryId);

        // THEN
        verify(categoryRepository).deleteById(existingCategoryId);
    }
}