package de.schnellertermin.backend.services.abstracts;

public interface IdService {
    String generateCategoryId();
    String generateComplaintId();
    String generateAppointmentId();
}
