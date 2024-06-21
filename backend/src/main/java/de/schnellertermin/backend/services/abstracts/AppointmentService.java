package de.schnellertermin.backend.services.abstracts;

import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;
import de.schnellertermin.backend.services.dtos.responses.AppointmentGetAllResponse;

import java.io.IOException;
import java.util.List;

public interface AppointmentService {
    List<AppointmentGetAllResponse> getAllAppointments();
    AppointmentCreatedResponse addAppointmentTask(AppointmentRequest appointmentRequest);
    AppointmentCreatedResponse updateAppointmentScore(String id, String imageUrl, String description) throws IOException;
    AppointmentCreatedResponse updateAppointmentStatus(String id, String status);
}
