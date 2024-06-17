package de.schnellertermin.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

}