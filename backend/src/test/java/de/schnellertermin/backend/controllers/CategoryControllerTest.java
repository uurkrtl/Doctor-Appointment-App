package de.schnellertermin.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.services.dtos.requests.CategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void getAllCategories_shouldReturnsListOfCategories() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getCategoryById_whenCategoryExists_shouldReturnCategory() throws Exception {
        // GIVEN
        String categoryId = "1";
        categoryRepository.save(Category.builder().id(categoryId).name("Test Category").build());

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/categories/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId));
    }

    @Test
    void getCategoryById_whenCategoryDoesNotExist_shouldReturnBadRequest() throws Exception {
        // GIVEN
        String categoryId = "1";

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/categories/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addCategory_whenRequestIsValid_returnCategoryCreatedResponse() throws Exception {
        // GIVEN
        CategoryRequest categoryRequest = CategoryRequest.builder().name("Test Category").build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/categories")
                .content(objectMapper.writeValueAsString(categoryRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addCategory_whenCategoryNameNull_shouldThrowBadRequest() throws Exception {
        // GIVEN
        CategoryRequest categoryRequest = CategoryRequest.builder().build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/categories")
                        .content(objectMapper.writeValueAsString(categoryRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateCategory_whenCategoryExists_shouldReturnCategory() throws Exception {
        // GIVEN
        String categoryId = "1";
        categoryRepository.save(Category.builder().id(categoryId).name("Test Category").build());
        CategoryRequest updatedCategory = CategoryRequest.builder().name("Updated Category").build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/categories/" + categoryId)
                        .content(objectMapper.writeValueAsString(updatedCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(categoryId));
    }

    @Test
    void updateCategory_whenCategoryDoesNotExist_shouldReturnNotFound() throws Exception {
        // GIVEN
        String categoryId = "1";
        CategoryRequest updatedCategory = CategoryRequest.builder().name("Updated Category").build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/categories/" + categoryId)
                        .content(objectMapper.writeValueAsString(updatedCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteCategory_whenCategoryIdExists_shouldDeleteCategory() throws Exception {
        // GIVEN
        String existingCategoryId = "NonExistingId";
        categoryRepository.save(Category.builder().id(existingCategoryId).name("Test Category").build());

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/categories/delete/" + existingCategoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCategory_whenCategoryIdDoesNotExists_shouldReturnNotFound() throws Exception {
        // GIVEN
        String existingCategoryId = "NonExistingId";

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/categories/delete/" + existingCategoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}