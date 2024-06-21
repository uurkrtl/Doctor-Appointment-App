package de.schnellertermin.backend.services.abstracts;

import de.schnellertermin.backend.services.dtos.requests.AppointmentRequest;
import de.schnellertermin.backend.services.dtos.responses.AppointmentCreatedResponse;

public interface AppointmentService {
    AppointmentCreatedResponse addAppointmentTask(AppointmentRequest appointmentRequest);
}
