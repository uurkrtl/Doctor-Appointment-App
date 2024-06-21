package de.schnellertermin.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentCreatedResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String complaintName;
    private String description;
    private LocalDate appointmentDate;
}
