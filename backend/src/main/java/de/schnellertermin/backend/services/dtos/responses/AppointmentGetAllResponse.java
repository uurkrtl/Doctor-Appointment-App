package de.schnellertermin.backend.services.dtos.responses;

import de.schnellertermin.backend.models.Complaint;
import de.schnellertermin.backend.models.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentGetAllResponse {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate appointmentDate;
    private Complaint complaint;
    private String description;
    private String imageUrl;
    private String urgencyScore;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
