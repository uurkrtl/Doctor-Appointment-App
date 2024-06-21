package de.schnellertermin.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.dtos.requests.ComplaintRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"open.ai.key=dummy-key"})
class ComplaintControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllComplaints_shouldReturnsListOfComplaints() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/complaints")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void addComplaint_whenRequestIsValid_returnComplaintCreatedResponse() throws Exception {
        // GIVEN
        Category category = Category.builder().name("Test Category").build();
        String  categoryId = categoryRepository.save(category).getId();
        ComplaintRequest complaintRequest = ComplaintRequest.builder().name("Test Complaint").categoryId(categoryId).build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/complaints")
                        .content(objectMapper.writeValueAsString(complaintRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addComplaint_whenComplaintNameNull_shouldThrowBadRequest() throws Exception {
        // GIVEN
        Category category = Category.builder().name("Test Category").build();
        String  categoryId = categoryRepository.save(category).getId();
        ComplaintRequest complaintRequest = ComplaintRequest.builder().categoryId(categoryId).build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/complaints")
                        .content(objectMapper.writeValueAsString(complaintRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addComplaint_whenComplaintNameExistiert_shouldThrowConflict() throws Exception {
        // GIVEN
        String complaintName = "Test Complaint";
        Category category = Category.builder().name("Test Category").build();
        String  categoryId = categoryRepository.save(category).getId();
        ComplaintRequest complaintRequest = ComplaintRequest.builder().name(complaintName).categoryId(categoryId).build();
        complaintRepository.save(Complaint.builder().name(complaintName).category(category).build());

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/complaints")
                        .content(objectMapper.writeValueAsString(complaintRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

}