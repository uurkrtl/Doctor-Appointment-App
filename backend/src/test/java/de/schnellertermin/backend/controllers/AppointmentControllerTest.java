package de.schnellertermin.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.schnellertermin.backend.models.Appointment;
import de.schnellertermin.backend.models.Category;
import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.repositories.AppointmentRepository;
import de.schnellertermin.backend.repositories.CategoryRepository;
import de.schnellertermin.backend.repositories.ComplaintRepository;
import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"open.ai.key=dummy-key"})
class AppointmentControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ComplaintRepository complaintRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void getAllAppointments_shouldReturnsListOfAppointments() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getAppointmentById_whenAppointmentExists_shouldReturnAppointment() throws Exception {
        // GIVEN
        String appointmentId = "TestId";
        appointmentRepository.save(Appointment.builder().id(appointmentId).build());

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/appointments/" + appointmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(appointmentId));
    }

    @Test
    void addAppointment_whenRequestIsValid_returnAppointmentCreatedResponse() throws Exception {
        // GIVEN
        Category category = categoryRepository.save(Category.builder().name("Test Category").build());
        String complaintId = complaintRepository.save(Complaint.builder().name("Test Complaint").category(category).build()).getId();
        AppointmentRequest appointmentRequest = AppointmentRequest.builder().complaintId(complaintId).build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/appointments")
                        .content(objectMapper.writeValueAsString(appointmentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void updateAppointmentStatus_whenRequestIsValid_shouldReturnAppointmentCreatedResponse() throws Exception {
        // GIVEN
        Category category = categoryRepository.save(Category.builder().name("Test Category").build());
        Complaint complaint = complaintRepository.save(Complaint.builder().name("Test Complaint").category(category).build());
        String appointmentId = appointmentRepository.save(Appointment.builder().complaint(complaint).build()).getId();
        String status = "ACTIVE";

        // WHEN & THEN
        mockMvc.perform(put("/api/appointments/set-status/{id}", appointmentId)
                        .param("status", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appointmentId))
                .andExpect(jsonPath("$.status").value(status));
    }

}