package de.schnellertermin.backend.models;

import de.schnellertermin.backend.models.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate appointmentDate;
    @DBRef
    private Complaint complaint;
    private String description;
    private String imageUrl;
    private Integer urgencyScore;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
