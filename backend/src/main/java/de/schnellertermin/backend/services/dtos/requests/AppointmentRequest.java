package de.schnellertermin.backend.services.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {
    private String firstName;
    private String lastName;
    private LocalDate appointmentDate;
    private String complaintId;
    private String description;
    private String imageUrl;
}
